package com.capstone.nutrilens.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.nutrilens.data.response.NewsListApiResponse
import com.capstone.nutrilens.data.response.NewsListData
import com.capstone.nutrilens.data.response.News
import com.capstone.nutrilens.data.response.NewsDetailApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    fun getAllNews(authorization: String): LiveData<NewsListApiResponse?> {
        return repository.getAllNews(authorization)
    }

    fun getNews(authorization: String, id: String): LiveData<NewsDetailApiResponse?> {
        return repository.getNews(authorization, id)
    }
}