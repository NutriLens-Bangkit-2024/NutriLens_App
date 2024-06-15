package com.capstone.nutrilens.ui.news

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepository(ApiService.instanceRetrofit)
        newsViewModel = ViewModelProvider(this, ViewModelFactory(repository))[NewsViewModel::class.java]

        val id = intent.getStringExtra("id") ?: return
        val authorization = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1cm46YXVkaWVuY2U6dGVzdCIsImlzcyI6InVybjppc3N1ZXI6dGVzdCIsInN1YiI6IlVZWnA3ZS1ZRHZFd0pXMHAiLCJpYXQiOjE3MTgzNzg1NzR9.5kUX07vwT7xNQLTAIDimRAb6UGIDXiyczHjbg5Gz4bQ"

        newsViewModel.getNews(authorization, id).observe(this, Observer { response ->
            response?.data?.news?.let { news ->
                Log.d("NewsActivity", "News Title: ${news.title}")
                binding.tvTitle.text = news.title
                binding.tvSourceNews.text = news.source
                binding.tvDescription.text = news.content
                Glide.with(this).load(news.image).into(binding.ivPicture)
            } ?: run {
                Log.d("NewsActivity", "News data is null")
            }
        })
    }
}