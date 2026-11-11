package com.example.notify.uiState

data class LogInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = "",
    val isLoggedIn: Boolean = false
)