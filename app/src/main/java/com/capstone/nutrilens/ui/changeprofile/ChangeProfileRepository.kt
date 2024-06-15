package com.capstone.nutrilens.ui.changeprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.data.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangeProfileRepository private constructor(
    private val apiService: ApiConfig
){
    suspend fun editUser(authorization: String, id: String, editUserRequest: EditUserRequest): LiveData<NetworkResult<UserResponse>> {
        val data = MutableLiveData<NetworkResult<UserResponse>>()
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.editUser(authorization, id, editUserRequest)
                if (response.isSuccessful) {
                    data.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    data.postValue(NetworkResult.Error("Failed to edit user profile"))
                }
            } catch (e: Exception) {
                data.postValue(NetworkResult.Error(e.message ?: "An error occurred"))
            }
        }
        return data
    }

    companion object {
        @Volatile
        private var instance: ChangeProfileRepository? = null
        fun getInstance(apiConfig: ApiConfig): ChangeProfileRepository =
            instance ?: synchronized(this) {
                instance ?: ChangeProfileRepository(apiConfig)
            }.also { instance = it }
    }
}
