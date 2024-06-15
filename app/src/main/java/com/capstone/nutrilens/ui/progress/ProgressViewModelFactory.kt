package com.capstone.nutrilens.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProgressViewModelFactory(private val repository: CaloriesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            return ProgressViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}