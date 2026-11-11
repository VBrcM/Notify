package com.example.notify.data

sealed class Results<out T> {
    data class Success<T>(val data: T) : Results<T>()
    data class Error(val exception: Exception) : Results<Nothing>()
    data object Loading : Results<Nothing>()
}