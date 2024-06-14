package com.capstone.nutrilens.ui.register

import androidx.lifecycle.liveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.RegisterResponse
import com.capstone.nutrilens.data.util.Result
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterRepository private constructor(
    private val apiConfig: ApiConfig
){
    fun registerUser(email:String,password:String, name:String) = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiConfig.registerUser(email,password,name,"https://storage.googleapis.com/bucket_nutrilens1/profile/profile1.png")
            emit(Result.Success(successResponse.message))
        }catch (e:HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    companion object{
        @Volatile
        private var instance : RegisterRepository? = null
        fun getInstance(apiConfig: ApiConfig): RegisterRepository =
            instance ?: synchronized(this){
                instance ?: RegisterRepository(apiConfig)
            }.also { instance = it }
    }
}