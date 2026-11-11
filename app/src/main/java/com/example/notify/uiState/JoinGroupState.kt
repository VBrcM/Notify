package com.example.notify.uiState

sealed class JoinGroupState {
    data object Idle : JoinGroupState()
    data object Loading : JoinGroupState()
    data object Success : JoinGroupState()
    data class Error(val message: String) : JoinGroupState()
    data object NavigateToGroupScreen : JoinGroupState()
}