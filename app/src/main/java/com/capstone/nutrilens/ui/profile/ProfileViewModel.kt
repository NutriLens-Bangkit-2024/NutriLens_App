package com.capstone.nutrilens.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.data.util.NetworkResult
import retrofit2.Response

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    fun getProfile(authorization: String, userId: String): LiveData<NetworkResult<UserResponse>> {
        return liveData {
            emitSource(repository.getProfile(authorization, userId))
        }
    }
}
