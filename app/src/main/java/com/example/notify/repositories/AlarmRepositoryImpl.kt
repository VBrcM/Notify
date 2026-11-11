package com.example.notify.repositories

import android.util.Log
import com.example.notify.data.AlarmGroup
import com.example.notify.data.Alarms
import com.example.notify.uiState.GroupsResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AlarmRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : AlarmRepository {

    override suspend fun saveAlarm(alarms: Alarms, selectedGroupId: String?) {
        if (selectedGroupId != null) {
            try {
                val alarmId = alarms.alarmId

                val alarmDocRef = firestore.collection("Groups").document(selectedGroupId)
                    .collection("Alarms").document(alarmId)
                alarmDocRef.set(alarms)

            } catch (e: Exception) {
                Log.e(TAG, "Error saving alarm", e)
            }
        } else {
            Log.e(TAG, "No group selected")
        }
    }

    override suspend fun getGroupsForUser(): GroupsResult {
        return try {
            val currentUser = auth.currentUser
            val userEmail = currentUser?.email
            if (userEmail != null) {
                val querySnapshot = firestore.collection("Groups")
                    .whereArrayContains("adminEmails", userEmail)
                    .get().await()

                val groups = mutableListOf<AlarmGroup>()
                for (document in querySnapshot.documents) {
                    val groupId = document.id
                    val groupName = document.getString("groupName") ?: ""
                    groups.add(AlarmGroup(groupId, groupName))
                }
                GroupsResult.Success(groups)
            } else {
                GroupsResult.Failure(IllegalStateException("User not authenticated or email is unavailable"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting groups for user", e)
            GroupsResult.Failure(e)
        }
    }

    override suspend fun getAlarmsForUser(): List<Alarms> {
        val currentUserEmail = auth.currentUser?.email
        if (currentUserEmail == null) {
            Log.e(TAG, "User not authenticated")
            return emptyList()
        }

        val alarms = mutableListOf<Alarms>()

        try {
            val querySnapshot = firestore.collection("Groups")
                .whereArrayContains("members", currentUserEmail)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                val groupId = document.id

                val alarmsSnapshot = firestore.collection("Groups").document(groupId)
                    .collection("Alarms")
                    .get()
                    .await()

                for (alarmDocument in alarmsSnapshot.documents) {
                    val alarmId = alarmDocument.getString("alarmId") ?: ""
                    val title = alarmDocument.getString("title") ?: ""
                    val description = alarmDocument.getString("description") ?: ""
                    val hour = alarmDocument.getLong("hour")?.toInt() ?: 0
                    val minute = alarmDocument.getLong("minute")?.toInt() ?: 0
                    val endHour = alarmDocument.getLong("endHour")?.toInt() ?: 0
                    val endMinute = alarmDocument.getLong("endMinute")?.toInt() ?: 0
                    val dateDisplay = alarmDocument.getString("dateDisplay") ?: ""
                    val endDateDisplay = alarmDocument.getString("endDateDisplay") ?: ""
                    val dInMillis = alarmDocument.getLong("dInMillis") ?: 0
                    val endDInMillis = alarmDocument.getLong("endDInMillis") ?: 0
                    val groupDisp = alarmDocument.getString("groupDisp") ?: ""
                    val currentGroupId = alarmDocument.getString("currentGroupId") ?: ""
                    val intervalDay = alarmDocument.getLong("intervalDay")?.toInt() ?: 0
                    val intervalHour = alarmDocument.getLong("intervalHour")?.toInt() ?: 0
                    val intervalMinute = alarmDocument.getLong("intervalMinute")?.toInt() ?: 0
                    val intervalInMillis = alarmDocument.getLong("intervalInMillis") ?: 0
                    val startTimeInMillis = alarmDocument.getLong("startTimeInMillis") ?: 0
                    val endTimeInMillis = alarmDocument.getLong("endTimeInMillis") ?: 0



                    val currentTimeInMillis = System.currentTimeMillis()


                    if (currentTimeInMillis > endTimeInMillis) {
                        val alarmDocRef = firestore.collection("Groups").document(groupId)
                            .collection("Alarms").document(alarmId)
                        alarmDocRef.delete().await()
                    } else {
                        val alarm = Alarms(alarmId, title, description, hour, minute, endHour, endMinute,
                            dateDisplay, endDateDisplay, dInMillis, endDInMillis, groupDisp, currentGroupId,
                            intervalDay, intervalHour, intervalMinute, intervalInMillis, startTimeInMillis, endTimeInMillis )
                        alarms.add(alarm)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting alarms for user", e)
        }

        return alarms
    }

    override suspend fun getAlarmById(currentGroupId: String, alarmId: String): Alarms? {
        try {
            val alarmDocRef = firestore.collection("Groups").document(currentGroupId)
                .collection("Alarms").document(alarmId)

            val alarmSnapshot = alarmDocRef.get().await()

            if (alarmSnapshot.exists()) {
                val title = alarmSnapshot.getString("title") ?: ""
                val description = alarmSnapshot.getString("description") ?: ""
                val hour = alarmSnapshot.getLong("hour")?.toInt() ?: 0
                val minute = alarmSnapshot.getLong("minute")?.toInt() ?: 0
                val endHour = alarmSnapshot.getLong("endHour")?.toInt() ?: 0
                val endMinute = alarmSnapshot.getLong("endMinute")?.toInt() ?: 0
                val dateDisplay = alarmSnapshot.getString("dateDisplay") ?: ""
                val endDateDisplay = alarmSnapshot.getString("endDateDisplay") ?: ""
                val dInMillis = alarmSnapshot.getLong("dInMillis") ?: 0
                val endDInMillis = alarmSnapshot.getLong("endDInMillis") ?: 0
                val groupDisp = alarmSnapshot.getString("groupDisp") ?: ""
                val currentGroupId = alarmSnapshot.getString("currentGroupId") ?: ""
                val intervalDay = alarmSnapshot.getLong("intervalDay")?.toInt() ?: 0
                val intervalHour = alarmSnapshot.getLong("intervalHour")?.toInt() ?: 0
                val intervalMinute = alarmSnapshot.getLong("intervalMinute")?.toInt() ?: 0
                val intervalInMillis = alarmSnapshot.getLong("intervalInMillis") ?: 0
                val startTimeInMillis = alarmSnapshot.getLong("startTimeInMillis") ?: 0
                val endTimeInMillis = alarmSnapshot.getLong("endTimeInMillis") ?: 0

                val currentTimeInMillis = System.currentTimeMillis()

                if (currentTimeInMillis > endTimeInMillis) {
                    alarmDocRef.delete().await()
                    return null
                }

                return Alarms(
                    alarmId, title, description, hour, minute, endHour, endMinute,
                    dateDisplay, endDateDisplay, dInMillis, endDInMillis, groupDisp, currentGroupId,
                    intervalDay, intervalHour, intervalMinute, intervalInMillis, startTimeInMillis, endTimeInMillis
                )
            } else {
                Log.e(TAG, "Alarm document does not exist")
                return null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting alarm by ID", e)
            return null
        }
    }

    override suspend fun deleteAlarm(alarmId: String, groupId: String) {
        try {
            val alarmDocRef = firestore.collection("Groups").document(groupId)
                .collection("Alarms").document(alarmId)
            alarmDocRef.delete().await()
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting alarm", e)
        }
    }

    override suspend fun isUserAdmin(currentGroupId: String): Boolean {
        val currentUserEmail = auth.currentUser?.email
        if (currentUserEmail == null) {
            Log.e(TAG, "User not authenticated")
            return false
        }

        try {
            val groupDocRef = firestore.collection("Groups").document(currentGroupId)
            val groupSnapshot = groupDocRef.get().await()

            if (groupSnapshot.exists()) {
                val adminEmails = groupSnapshot.get("adminEmails") as? List<String>
                return adminEmails?.contains(currentUserEmail) ?: false
            } else {
                Log.e(TAG, "Group document does not exist")
                return false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if user is admin", e)
            return false
        }
    }
            companion object {
        const val TAG = "AlarmRepositoryImpl"
    }
}