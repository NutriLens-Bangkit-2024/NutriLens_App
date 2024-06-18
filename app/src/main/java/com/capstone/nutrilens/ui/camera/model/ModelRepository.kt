package com.capstone.nutrilens.ui.camera.model

import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.liveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.ModelTestingResponse
import com.capstone.nutrilens.data.util.Result
import com.google.gson.Gson
import retrofit2.HttpException

class ModelRepository private constructor(
    private val apiConfig: ApiConfig
) {
    fun getModelResponse(modelRequest: ModelRequest) = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiConfig.getModelResult(modelRequest)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ModelTestingResponse::class.java)
            emit(Result.Error(errorResponse.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ModelRepository? = null
        fun getInstance(apiConfig: ApiConfig): ModelRepository =
            instance ?: synchronized(this) {
                instance ?: ModelRepository(apiConfig)
            }.also { instance = it }
    }
}