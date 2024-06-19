package com.capstone.nutrilens.ui.camera.predict

import androidx.lifecycle.liveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.PredictionResponse
import com.capstone.nutrilens.data.util.Result
import com.google.gson.Gson
import retrofit2.HttpException

class PredictionRepository private constructor(
    private val apiConfig: ApiConfig
){
    fun savePredictionResult(token:String, label: String, calories: Int) = liveData {
        emit(Result.Loading)
        try {
            val request = PredictionRequest(label,calories)
            val successResponse = apiConfig.savePrediction("Bearer $token",request)
            emit(Result.Success(successResponse.message))
        }catch (e:HttpException){
            val errorBody = e.response()?.errorBody()?.toString()
            val errorResponse = Gson().fromJson(errorBody,PredictionResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    companion object{
        @Volatile
        private var instance: PredictionRepository? = null
        fun getInstance(apiConfig: ApiConfig) : PredictionRepository =
            instance?: synchronized(this){
                instance?:PredictionRepository(apiConfig)
            }.also { instance= it }
    }
}