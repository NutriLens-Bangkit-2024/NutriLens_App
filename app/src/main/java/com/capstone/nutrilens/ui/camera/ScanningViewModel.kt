package com.capstone.nutrilens.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.nutrilens.data.response.ScanningTestingResponse
import com.capstone.nutrilens.data.util.NetworkResult
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanningViewModel (private val repository: ScanningRepository): ViewModel() {

    fun getScanningResponse(token: String, imageFile : File) = repository.getScanningResponse(token,imageFile)
//    private val _scanningResult = MutableLiveData<NetworkResult<ScanningTestingResponse>>()
//    val scanningResult: LiveData<NetworkResult<ScanningTestingResponse>> = _scanningResult
//
//    fun getScanningResponse(token: String, multipartBody : MultipartBody.Part) {
//        _scanningResult.value = NetworkResult.Loading(true)
//        val successResponse = repository.getScanningResult(token,multipartBody)
//        successResponse.enqueue(object : Callback<ScanningTestingResponse> {
//            override fun onResponse(call: Call<ScanningTestingResponse>, response: Response<ScanningTestingResponse>) {
//                if (response.isSuccessful) {
//                    _scanningResult.value = NetworkResult.Success(response.body()!!)
//                } else {
//                    _scanningResult.value = NetworkResult.Error(response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<ScanningTestingResponse>, t: Throwable) {
//                _scanningResult.value = NetworkResult.Error(t.message.toString())
//            }
//        })
//    }

}