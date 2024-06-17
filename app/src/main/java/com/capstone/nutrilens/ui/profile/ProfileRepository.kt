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

class ProfileRepository(private val apiConfig: ApiConfig) {
    suspend fun getUser(token: String, id: String): NetworkResult<UserResponse> {
        return try {
            val response = apiConfig.getUser(token, id)
            if (response.isSuccessful) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Exception: ${e.message}")
        }
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