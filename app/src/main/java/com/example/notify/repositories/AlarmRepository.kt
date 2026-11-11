package com.example.notify.repositories

import com.example.notify.data.Alarms
import com.example.notify.uiState.GroupsResult


interface AlarmRepository {
    suspend fun getGroupsForUser(): GroupsResult
    suspend fun saveAlarm(alarms: Alarms, selectedGroupId: String?)
    suspend fun getAlarmsForUser(): List<Alarms>
    suspend fun deleteAlarm(alarmId: String, groupId: String)
    suspend fun getAlarmById(currentGroupId: String, alarmId: String): Alarms?
    suspend fun isUserAdmin(currentGroupId: String): Boolean
}
