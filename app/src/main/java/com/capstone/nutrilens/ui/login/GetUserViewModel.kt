package com.capstone.nutrilens.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.data.util.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GetUserViewModel : ViewModel(){
    private val userResponse = MutableLiveData<NetworkResult<UserResponse>>()

    fun getUser(id: String, token: String) {
        viewModelScope.launch {
            flow {
                val response = ApiService.instanceRetrofit.getUser(id)
                emit(response)
            }.onStart {
                userResponse.value = NetworkResult.Loading(true)
            }.onCompletion {
                userResponse.value = NetworkResult.Loading(false)
            }.catch { exception ->
                userResponse.value = NetworkResult.Error(exception.message ?: "Unknown error")
            }.collect { response ->
                if (response.isSuccessful) {
                    userResponse.value = NetworkResult.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    userResponse.value = NetworkResult.Error(errorBody ?: "Unknown error")
                }
            }
        }
    }
}