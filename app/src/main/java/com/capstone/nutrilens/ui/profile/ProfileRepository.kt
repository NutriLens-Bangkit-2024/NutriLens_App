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
    suspend fun getProfile(authorization: String, userId: String): LiveData<NetworkResult<UserResponse>> {
        val result = MutableLiveData<NetworkResult<UserResponse>>()
        result.postValue(NetworkResult.Loading(true))

        try {
            val response = apiService.getUser(authorization, userId)
            if (response.isSuccessful) {
                result.postValue(NetworkResult.Success(response.body()!!))
            } else {
                val errorBody = response.errorBody()?.string()
                result.postValue(NetworkResult.Error(errorBody ?: "Unknown error"))
            }
        } catch (e: Exception) {
            result.postValue(NetworkResult.Error(e.message ?: "Unknown error"))
        } finally {
            result.postValue(NetworkResult.Loading(false))
        }
        return result
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