package com.capstone.nutrilens.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.NewsApiResponse
import com.capstone.nutrilens.data.response.NewsData
import com.capstone.nutrilens.data.response.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val apiService: ApiConfig) {

    fun getAllNews(authorization: String): LiveData<NewsApiResponse<NewsData>?> {
        val data = MutableLiveData<NewsApiResponse<NewsData>?>()
        apiService.getAllNews(authorization).enqueue(object : Callback<NewsApiResponse<NewsData>> {
            override fun onResponse(
                call: Call<NewsApiResponse<NewsData>>,
                response: Response<NewsApiResponse<NewsData>>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<NewsApiResponse<NewsData>>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun getNews(authorization: String, id: String): Call<NewsApiResponse<News>> {
        return apiService.getNews(authorization, id)
    }
}

