package com.capstone.nutrilens.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.ui.recipe.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProfileRepository private constructor(
    private val apiService: ApiConfig
){
    suspend fun getProfile(token: String): LiveData<NetworkResult<UserResponse>> {
        val data = MutableLiveData<NetworkResult<UserResponse>>()
        withContext(Dispatchers.IO) {
            try {
                val authorization = "Bearer $token"
                val response = apiService.getUser(authorization)
                if (response.isSuccessful) {
                    data.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    data.postValue(NetworkResult.Error("Failed to get user profile"))
                }
            } catch (e: Exception) {
                data.postValue(NetworkResult.Error(e.message ?: "An error occurred"))
            }
        }
        return data
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null
        fun getInstance(apiConfig: ApiConfig): ProfileRepository =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(apiConfig)
            }.also { instance = it }
    }
}