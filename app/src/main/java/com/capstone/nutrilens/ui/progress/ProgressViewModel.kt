package com.capstone.nutrilens.ui.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.CaloriesResponse
import com.capstone.nutrilens.data.util.NetworkResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgressViewModel(private val repository: CaloriesRepository) : ViewModel() {
    private val _caloriesResult = MutableLiveData<NetworkResult<CaloriesResponse>>()
    val caloriesResult: LiveData<NetworkResult<CaloriesResponse>> = _caloriesResult

    fun fetchCaloriesData(authorization: String) {
        _caloriesResult.value = NetworkResult.Loading(true)
        repository.getCalories(authorization).enqueue(object : Callback<CaloriesResponse> {
            override fun onResponse(call: Call<CaloriesResponse>, response: Response<CaloriesResponse>) {
                if (response.isSuccessful) {
                    _caloriesResult.value = NetworkResult.Success(response.body()!!)
                } else {
                    _caloriesResult.value = NetworkResult.Error(response.message())
                }
            }

            override fun onFailure(call: Call<CaloriesResponse>, t: Throwable) {
                _caloriesResult.value = NetworkResult.Error(t.message.toString())
            }
        })
    }
}