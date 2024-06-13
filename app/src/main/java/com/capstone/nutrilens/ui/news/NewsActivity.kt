package com.capstone.nutrilens.ui.news

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepository(ApiService.instanceRetrofit)
        newsViewModel = ViewModelProvider(this, ViewModelFactory(repository))[NewsViewModel::class.java]

        val id = intent.getStringExtra("id") ?: return
        val authorization = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1cm46YXVkaWVuY2U6dGVzdCIsImlzcyI6InVybjppc3N1ZXI6dGVzdCIsInN1YiI6IlpkXzczQ2ktU2dpanVzSU0iLCJleHAiOjE3MTgzNDMxMDQsImlhdCI6MTcxODI1NjcwNH0.kEDTcIlg60nLEtxDg_42poFf5gKaeL90-5kd7LVWwrw"

        newsViewModel.getNews(authorization, id).observe(this, Observer { response ->
            response?.data?.let {
                binding.tvTitle.text = it.title
                binding.tvSourceNews.text = it.source
                binding.tvDescription.text = it.content
                // Set other views as needed
            }
        })
    }
}
