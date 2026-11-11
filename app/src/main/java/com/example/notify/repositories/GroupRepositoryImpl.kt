package com.example.notify.repositories



import com.example.notify.data.Groups
import com.example.notify.data.generateUniqueId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class GroupRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : GroupRepository {

    override suspend fun getGroupDetails(groupId: String): Groups {
        return try {
            val querySnapshot = firestore.collection("Groups")
                .whereEqualTo("groupId", groupId)
                .get().await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                document.toObject(Groups::class.java) ?: throw IllegalStateException("Group document is null")
            } else {
                throw IllegalStateException("Group not found with ID: $groupId")
            }
        } catch (e: Exception) {
            throw IllegalStateException("Failed to retrieve group details: ${e.message}")
        }
    }

    override suspend fun createGroup(groupData: Groups): String {
        val currentUser = auth.currentUser
        val currentUserEmail = currentUser?.email ?: throw IllegalStateException("User not authenticated")

        return try {
            val groupId = generateUniqueId()

            val groupWithId = groupData.copy(groupId = groupId)

            firestore.collection("Groups").document(groupId).set(groupWithId).await()

            firestore.collection("Groups").document(groupId)
                .update("groupId", groupId).await()

            groupId
        } catch (e: Exception) {
            throw IllegalStateException("Failed to create group: ${e.message}")
        }
    }

    override suspend fun joinGroup(groupId: String) {
        try {
            val currentUser = auth.currentUser
            val currentUserEmail = currentUser?.email ?: throw IllegalStateException("User not authenticated")

            val querySnapshot = firestore.collection("Groups")
                .whereEqualTo("groupId", groupId)
                .get().await()

            for (document in querySnapshot.documents) {
                val currentMembers = document.toObject(Groups::class.java)?.members ?: emptyList()

                if (!currentMembers.contains(currentUserEmail)) {
                    val updatedMembers = currentMembers.toMutableList().apply { add(currentUserEmail) }

                    firestore.collection("Groups").document(document.id)
                        .update("members", updatedMembers)
                        .await()
                }
            }
        } catch (e: Exception) {
            throw IllegalStateException("Failed to join group: ${e.message}")
        }
    }

    override suspend fun leaveGroup(groupId: String) {
        try {
            val currentUser = auth.currentUser
            val currentUserEmail = currentUser?.email ?: throw IllegalStateException("User not authenticated")

            val querySnapshot = firestore.collection("Groups")
                .whereEqualTo("groupId", groupId)
                .get().await()

            for (document in querySnapshot.documents) {
                val group = document.toObject(Groups::class.java)

                if (group != null && group.members.contains(currentUserEmail)) {
                    val updatedMembers = group.members.toMutableList().apply { remove(currentUserEmail) }

                    val updatedAdmins = group.adminEmails.toMutableList().apply { remove(currentUserEmail) }

                    val updatedCreator = if (group.creatorEmail == currentUserEmail) "" else group.creatorEmail

                    firestore.collection("Groups").document(document.id)
                        .update(
                            mapOf(
                                "members" to updatedMembers,
                                "adminEmails" to updatedAdmins,
                                "creatorEmail" to updatedCreator
                            )
                        ).await()

                    if (updatedAdmins.isEmpty() && updatedMembers.isNotEmpty()) {
                        val newAdmin = updatedMembers.first()
                        firestore.collection("Groups").document(document.id)
                            .update("adminEmails", listOf(newAdmin))
                            .await()
                    }
                    if (updatedMembers.isEmpty() && updatedAdmins.isEmpty()) {
                        firestore.collection("Groups").document(document.id)
                            .delete()
                            .await()
                    }
                }
            }
        } catch (e: Exception) {
            throw IllegalStateException("Failed to leave group: ${e.message}")
        }
    }

    override suspend fun getGroupsForCurrentUser(): List<Groups> {
        return try {
            val currentUser = auth.currentUser
            val currentUserEmail = currentUser?.email ?: throw IllegalStateException("User not authenticated")

            val querySnapshot = firestore.collection("Groups")
                .whereArrayContains("members", currentUserEmail)
                .get().await()

            val groups = mutableListOf<Groups>()
            for (document in querySnapshot.documents) {
                val group = document.toObject(Groups::class.java)
                group?.let {
                    val memberCount = it.members.size.toLong()
                    val updatedGroup = it.copy(memberCount = memberCount)
                    groups.add(updatedGroup)
                }
            }
            groups
        } catch (e: Exception) {
            throw IllegalStateException("Failed to retrieve groups: ${e.message}")
        }
    }
}