package com.example.notify.viewModels


import androidx.lifecycle.ViewModel
import com.example.notify.sharedPrefManager.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val auth : FirebaseAuth
) : ViewModel() {
    fun logoutUser(onComplete: () -> Unit, onError: (String) -> Unit) {
        auth.signOut()
        sharedPreferencesManager.clearUserData()
        onComplete()
    }
}