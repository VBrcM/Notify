package com.example.notify.repositories

import com.example.notify.data.Groups

interface GroupRepository {
    suspend fun createGroup(groupData: Groups): String
    suspend fun joinGroup(groupId: String)
    suspend fun leaveGroup(groupId: String)
    suspend fun getGroupsForCurrentUser(): List<Groups>
    suspend fun getGroupDetails(groupId: String): Groups
}