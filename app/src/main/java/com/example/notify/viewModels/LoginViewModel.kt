package com.example.notify.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.repositories.AuthRepository
import com.example.notify.data.Resource
import com.example.notify.sharedPrefManager.SharedPreferencesManager
import com.example.notify.uiState.LogInState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val firebaseAuth: FirebaseAuth,
    val sharedPreferencesManager: SharedPreferencesManager
):ViewModel() {
    private val _loginState = Channel<LogInState>()
    val loginState = _loginState.receiveAsFlow()


    fun loginUser(email:String, password:String)= viewModelScope.launch {
        val isLoggedInBefore = firebaseAuth.currentUser != null
        repository.loginUser(email, password).collect {result->
            if (isLoggedInBefore != (firebaseAuth.currentUser != null)) {
                _loginState.send(LogInState(isLoggedIn = firebaseAuth.currentUser != null))
            }

            when (result) {
                is Resource.Success -> {
                    _loginState.send(LogInState(isSuccess = "Log In Success"))
                }

                is Resource.Loading -> {
                    _loginState.send(LogInState(isLoading = true))
                }

                is Resource.Error -> {
                    _loginState.send(LogInState(isError = result.message))
                }
            }
            if (result is Resource.Success) {
                sharedPreferencesManager.setLoggedIn(true)
            }
        }
    }
}