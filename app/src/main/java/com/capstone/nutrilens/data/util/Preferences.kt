package com.capstone.nutrilens.data.util

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val TOKEN_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1cm46YXVkaWVuY2U6dGVzdCIsImlzcyI6InVybjppc3N1ZXI6dGVzdCIsInN1YiI6IlpkXzczQ2ktU2dpanVzSU0iLCJleHAiOjE3MTgzNDMxMDQsImlhdCI6MTcxODI1NjcwNH0.kEDTcIlg60nLEtxDg_42poFf5gKaeL90-5kd7LVWwrw"
    }

    fun saveToken(token: String) {
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearSession(){
        editor.clear()
        editor.apply()
    }
}