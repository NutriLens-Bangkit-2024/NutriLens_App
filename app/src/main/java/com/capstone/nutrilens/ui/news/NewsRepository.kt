package com.capstone.nutrilens.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.NewsListApiResponse
import com.capstone.nutrilens.data.response.NewsDetailApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val apiService: ApiConfig) {

    fun getAllNews(authorization: String): LiveData<NewsListApiResponse?> {
        val data = MutableLiveData<NewsListApiResponse?>()
        apiService.getAllNews(authorization).enqueue(object : Callback<NewsListApiResponse> {
            override fun onResponse(
                call: Call<NewsListApiResponse>,
                response: Response<NewsListApiResponse>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<NewsListApiResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun getNews(authorization: String, id: String): LiveData<NewsDetailApiResponse?> {
        val data = MutableLiveData<NewsDetailApiResponse?>()
        apiService.getNews(authorization, id).enqueue(object : Callback<NewsDetailApiResponse> {
            override fun onResponse(call: Call<NewsDetailApiResponse>, response: Response<NewsDetailApiResponse>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<NewsDetailApiResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}