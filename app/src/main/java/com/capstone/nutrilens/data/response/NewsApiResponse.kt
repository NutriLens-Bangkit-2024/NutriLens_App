package com.capstone.nutrilens.data.response

data class NewsApiResponse<T>(
    val status: String,
    val data: T
)


