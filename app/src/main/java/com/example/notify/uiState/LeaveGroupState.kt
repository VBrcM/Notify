package com.example.notify.uiState


sealed class LeaveGroupState {
    data object Idle : LeaveGroupState()
    data object Loading : LeaveGroupState()
}