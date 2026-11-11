package com.example.notify.uiState

import com.example.notify.data.Groups

data class GroupScreenState(
    val groupInfo: Groups,
    val isLoading: Boolean,
    val error: String?
)