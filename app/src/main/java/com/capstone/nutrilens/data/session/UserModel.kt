package com.capstone.nutrilens.data.session

data class UserModel(
    val token: String,
    val userId: String,
    val isLogin: Boolean = false
)