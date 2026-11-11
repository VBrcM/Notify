package com.example.notify.uiState

import com.example.notify.data.AlarmGroup

sealed class GroupsResult {
    data class Success(val groups: List<AlarmGroup>) : GroupsResult()
    data class Failure(val error: Exception) : GroupsResult()
    data object Loading : GroupsResult()
}