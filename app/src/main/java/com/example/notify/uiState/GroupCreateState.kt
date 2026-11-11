package com.example.notify.uiState

sealed class GroupCreateState {
    data object Loading : GroupCreateState()
    data class Success(val groupId: String) : GroupCreateState()
    data class Error(val errorMessage: String?) : GroupCreateState()
}