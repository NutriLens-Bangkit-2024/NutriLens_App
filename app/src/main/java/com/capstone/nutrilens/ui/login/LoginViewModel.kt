package com.capstone.nutrilens.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.response.LoginRequest
import com.capstone.nutrilens.data.response.LoginResponse
import com.capstone.nutrilens.data.util.NetworkResult
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val loginResponse = MutableLiveData<NetworkResult<LoginResponse>>()

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginResponse.value = NetworkResult.Loading(true)
            try {
                val response = ApiService.instanceRetrofit.login(loginRequest)
                if (response.isSuccessful) {
                    loginResponse.value = NetworkResult.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    loginResponse.value = NetworkResult.Error(errorBody ?: "Unknown error")
                }
            } catch (e: Exception) {
                loginResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            } finally {
                loginResponse.value = NetworkResult.Loading(false)
            }
        }
    }
}