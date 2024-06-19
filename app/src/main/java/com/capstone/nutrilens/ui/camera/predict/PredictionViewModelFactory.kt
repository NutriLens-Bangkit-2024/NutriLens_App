package com.capstone.nutrilens.ui.camera.predict

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection

class PredictionViewModelFactory (private val repository: PredictionRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PredictionViewModel::class.java)){
            return PredictionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: PredictionViewModelFactory? = null
        fun getInstance(context: Context):PredictionViewModelFactory =
            instance?: synchronized(this){
                instance?:PredictionViewModelFactory(Injection.providePredictionRepository())
            }.also { instance = it }
    }
}