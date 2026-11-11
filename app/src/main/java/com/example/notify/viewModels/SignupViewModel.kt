package com.example.notify.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.uiState.LogInState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val auth: FirebaseAuth, // Inject FirebaseAuth for authentication
    private val firestore: FirebaseFirestore // Inject FirebaseFirestore for Firestore
) : ViewModel() {
    val _signupState = Channel<LogInState>()
    val signupState = _signupState.receiveAsFlow()

    fun signupUser(email: String, password: String, username: String) = viewModelScope.launch {
        _signupState.send(LogInState(isLoading = true)) // Notify loading state
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            val user = authResult.user
            val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            user?.updateProfile(profileUpdate)?.await()
            saveUserDataToFirestore(username, email)
            _signupState.send(LogInState(isSuccess = "Sign Up Success"))
        } catch (e: Exception) {
            _signupState.send(LogInState(isError = e.message))
        }
    }

    private suspend fun saveUserDataToFirestore(username: String, email: String) {
        val user = hashMapOf(
            "username" to username,
            "email" to email
        )
        try {
            firestore.collection("Users").document(email).set(user).await()
        } catch (e: Exception) {
            // Handle error if any
            Log.e("SignupViewModel", "Error saving user data to Firestore: ${e.message}", e)
        }
    }
}