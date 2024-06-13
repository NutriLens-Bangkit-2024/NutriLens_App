package com.capstone.nutrilens.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.nutrilens.data.response.NewsApiResponse
import com.capstone.nutrilens.data.response.NewsData
import com.capstone.nutrilens.data.response.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    fun getAllNews(authorization: String): LiveData<NewsApiResponse<NewsData>?> {
        return repository.getAllNews(authorization)
    }

    fun getNews(authorization: String, id: String): LiveData<NewsApiResponse<News>?> {
        val data = MutableLiveData<NewsApiResponse<News>?>()
        repository.getNews(authorization, id).enqueue(object : Callback<NewsApiResponse<News>> {
            override fun onResponse(call: Call<NewsApiResponse<News>>, response: Response<NewsApiResponse<News>>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<NewsApiResponse<News>>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}


