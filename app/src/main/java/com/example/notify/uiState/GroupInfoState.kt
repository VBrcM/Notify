package com.example.notify.uiState

import com.example.notify.data.Groups


sealed class GroupInfoState {
    data object Idle : GroupInfoState()
    data object Loading : GroupInfoState()
    data class Success(val group: Groups) : GroupInfoState()
    data class Error(val message: String) : GroupInfoState()
}