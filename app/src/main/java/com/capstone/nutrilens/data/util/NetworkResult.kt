package com.capstone.nutrilens.data.util

sealed class NetworkResult<T>(
    val data: T? = null,
    val exception: String? = null,
    val isLoading: Boolean = false
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(exception: String, data: T? = null) : NetworkResult<T>(data, exception)
    class Loading<T>(isLoading: Boolean) : NetworkResult<T>(null, null, isLoading)
}
