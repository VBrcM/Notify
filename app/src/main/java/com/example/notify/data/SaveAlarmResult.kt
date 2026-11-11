package com.example.notify.data

sealed class SaveAlarmResult {
    data class Success(val alarms: Result<List<Alarms>>) : SaveAlarmResult()
    data class Error(val exception: Exception) : SaveAlarmResult()
    data object Loading : SaveAlarmResult()
}