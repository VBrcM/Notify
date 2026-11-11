package com.example.notify.uiState

import com.example.notify.data.Alarms


sealed class AlarmsResult {
    data object Loading : AlarmsResult()
    data class Success(val alarms: List<Alarms>) : AlarmsResult()
    data class Failure(val error: Throwable) : AlarmsResult()
}