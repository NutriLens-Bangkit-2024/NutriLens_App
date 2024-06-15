package com.capstone.nutrilens.ui.progress

import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.response.CaloriesResponse
import retrofit2.Call

class CaloriesRepository(private val apiService: ApiConfig) {
    fun getCalories(authorization: String): Call<CaloriesResponse> {
        return apiService.getCalories(authorization)
    }
}