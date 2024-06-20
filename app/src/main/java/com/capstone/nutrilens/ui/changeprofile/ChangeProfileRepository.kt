package com.capstone.nutrilens.ui.changeprofile

import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.response.EditUserResponse
import retrofit2.Response

class ChangeProfileRepository private constructor(
    private val apiService: ApiConfig
){
    suspend fun editUser(authorization: String, id: String, editUserRequest: EditUserRequest): Response<EditUserResponse> {
        return apiService.editUser(authorization, id, editUserRequest)
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