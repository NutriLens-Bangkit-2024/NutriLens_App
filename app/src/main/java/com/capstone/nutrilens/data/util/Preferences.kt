package com.capstone.nutrilens.data.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class Preferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveToken(token: String) {
        try {
            editor.putString("TOKEN", token)
            editor.apply()
            Log.d("Preferences", "Token saved successfully: $token")
        } catch (e: Exception) {
            Log.e("Preferences", "Error saving token: ${e.message}")
        }
    }

    fun getToken(): String? {
        return try {
            val token = sharedPreferences.getString("TOKEN", null)
            Log.d("Preferences", "Token retrieved successfully: $token")
            token
        } catch (e: Exception) {
            Log.e("Preferences", "Error retrieving token: ${e.message}")
            null
        }
    }

    fun clearSession(){
        editor.clear()
        editor.apply()
    }
}