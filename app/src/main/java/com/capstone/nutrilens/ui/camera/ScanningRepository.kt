package com.capstone.nutrilens.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.RegisterResponse
import com.capstone.nutrilens.data.response.ScanningTestingResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class ScanningRepository private constructor(
    private val apiConfig: ApiConfig
){
    private val _scanningResult = MutableLiveData<Result<ScanningTestingResponse>>()
    val scanningResult : LiveData<Result<ScanningTestingResponse>> = _scanningResult

    fun getScanningResponse (token:String,imageFile: File) = liveData {
        emit(com.capstone.nutrilens.data.util.Result.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try{
            val successResponse = apiConfig.getScanningResult(token,multipartBody)
            emit(com.capstone.nutrilens.data.util.Result.Success(successResponse))
        }catch (e:HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ScanningTestingResponse::class.java)
            emit(com.capstone.nutrilens.data.util.Result.Error("Gagal untuk mendapatkan data"))
        }
    }

    companion object{
        @Volatile
        private var instance: ScanningRepository? = null
        fun getInstance(apiConfig: ApiConfig):ScanningRepository=
            instance?: synchronized(this){
                instance?:ScanningRepository(apiConfig)
            }.also { instance = it }
    }
}