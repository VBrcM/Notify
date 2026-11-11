package com.example.notify.uiState

import com.example.notify.data.Alarms

data class DashboardUiState(
    val alarms: List<Alarms> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)