package com.capstone.nutrilens.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.nutrilens.data.response.NewsListApiResponse
import com.capstone.nutrilens.data.response.NewsDetailApiResponse

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    fun getAllNews(authorization: String): LiveData<NewsListApiResponse?> {
        return repository.getAllNews(authorization)
    }

    fun getNews(authorization: String, id: String): LiveData<NewsDetailApiResponse?> {
        return repository.getNews(authorization, id)
    }
}