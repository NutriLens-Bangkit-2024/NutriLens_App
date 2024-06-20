package com.capstone.nutrilens.ui.login

import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.LoginRequest
import com.capstone.nutrilens.data.response.LoginResponse
import retrofit2.Response

class LoginRepository private constructor(
    private val apiService: ApiConfig)
{
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(email, password))
    }

    companion object{
        @Volatile
        private var instance : LoginRepository? = null
        fun getInstance(apiConfig: ApiConfig): LoginRepository =
            instance?: synchronized(this){
                instance?: LoginRepository(apiConfig)
            }.also { instance = it }
    }
}