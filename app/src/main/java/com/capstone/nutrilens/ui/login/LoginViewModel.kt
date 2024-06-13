package com.capstone.nutrilens.ui.login


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.response.LoginRequest
import com.capstone.nutrilens.data.response.LoginResponse
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.data.util.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val loginResponse = MutableLiveData<NetworkResult<LoginResponse>>()
    val userResponse = MutableLiveData<NetworkResult<UserResponse>>()

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

    fun getUser(id: String, token: String) {
        viewModelScope.launch {
            userResponse.value = NetworkResult.Loading(true)
            try {
                val response = ApiService.instanceRetrofit.getUser(id, token)
                if (response.isSuccessful) {
                    userResponse.value = NetworkResult.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    userResponse.value = NetworkResult.Error(errorBody ?: "Unknown error")
                }
            } catch (e: Exception) {
                userResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            } finally {
                userResponse.value = NetworkResult.Loading(false)
            }
        }
    }
}

