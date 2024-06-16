package com.capstone.nutrilens.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.ScanningTestingResponse

class ScanningRepository private constructor(
    private val apiConfig: ApiConfig
){
    private val _scanningResult = MutableLiveData<Result<ScanningTestingResponse>>()
    val scanningResult : LiveData<Result<ScanningTestingResponse>> = _scanningResult

//    fun getSuccessResponse (token:String) = liveData {
//        emit(com.capstone.nutrilens.data.util.Result.Loading)
//        try{
//            val successResponse = apiConfig.getScanningResult(token)
//        }
//    }

    companion object{
        @Volatile
        private var instance: ScanningRepository? = null
        fun getInstance(apiConfig: ApiConfig):ScanningRepository=
            instance?: synchronized(this){
                instance?:ScanningRepository(apiConfig)
            }.also { instance = it }
    }
}