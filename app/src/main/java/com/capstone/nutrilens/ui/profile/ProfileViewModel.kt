package com.capstone.nutrilens.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.data.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val _user = MutableLiveData<NetworkResult<UserResponse>>()
    val user: LiveData<NetworkResult<UserResponse>> get() = _user

    fun fetchUser(token: String, id: String) {
        viewModelScope.launch {
            _user.value = NetworkResult.Loading(true)
            val result = repository.getUser(token, id)
            _user.value = result
        }
    }
}