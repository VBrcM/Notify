package com.example.notify.repositories

import com.example.notify.data.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email:String, password:String): Flow<Resource<AuthResult>>
    fun signupUser(email: String, password: String): Flow<Resource<AuthResult>>
}