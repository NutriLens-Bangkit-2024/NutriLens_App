package com.capstone.nutrilens.data.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
//import at.favre.lib.crypto.bcrypt.BCrypt

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

    fun saveUserId(id: String) {
        try {
            editor.putString("id", id)
            editor.apply()
            Log.d("Preferences", "User ID saved successfully: $id")
        } catch (e: Exception) {
            Log.e("Preferences", "Error saving user ID: ${e.message}")
        }
    }

    fun getUserId(): String? {
        return try {
            val id = sharedPreferences.getString("id", null)
            Log.d("Preferences", "User ID retrieved successfully: $id")
            id
        } catch (e: Exception) {
            Log.e("Preferences", "Error retrieving user ID: ${e.message}")
            null
        }
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
}