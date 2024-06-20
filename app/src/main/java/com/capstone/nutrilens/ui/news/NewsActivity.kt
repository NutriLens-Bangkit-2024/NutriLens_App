package com.capstone.nutrilens.ui.news

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepository(ApiService.instanceRetrofit)
        newsViewModel = ViewModelProvider(this, ViewModelFactory(repository))[NewsViewModel::class.java]

        val id = intent.getStringExtra("id") ?: return
        val token = Preferences(this).getToken().toString()
        newsViewModel.getNews("Bearer $token", id).observe(this, Observer { response ->
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