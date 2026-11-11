package com.example.notify.uiState

import com.example.notify.data.Alarms

sealed class AlarmInfoState {
    data object Idle : AlarmInfoState()
    data object Loading : AlarmInfoState()
    data class Success(val alarm: Alarms) : AlarmInfoState()
    data class Error(val message: String) : GroupInfoState()
}