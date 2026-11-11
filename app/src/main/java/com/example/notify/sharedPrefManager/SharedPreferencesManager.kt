package com.example.notify.sharedPrefManager


import android.content.Context
import javax.inject.Inject


public class SharedPreferencesManager @Inject constructor (context: Context) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun clearUserData() {
        prefs.edit().clear().apply()
    }
}