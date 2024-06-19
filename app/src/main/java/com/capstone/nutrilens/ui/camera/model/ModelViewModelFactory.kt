package com.capstone.nutrilens.ui.camera.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection

class ModelViewModelFactory (private val repository: ModelRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModelViewModel::class.java)){
            return ModelViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ModelViewModelFactory?=null
        fun getInstance(context:Context): ModelViewModelFactory=
            instance?: synchronized(this){
                instance?:ModelViewModelFactory(Injection.provideModelRepository())
            }.also { instance = it }
    }
}