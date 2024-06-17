package com.capstone.nutrilens.ui.camera

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection

class ScanningViewModelFactory (private val scanningRepository : ScanningRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanningViewModel::class.java)){
            return ScanningViewModel(scanningRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        private var instance : ScanningViewModelFactory? = null
        fun getInstance(context: Context) : ScanningViewModelFactory =
            instance?: synchronized(this){
                instance?: ScanningViewModelFactory(Injection.provideScanningRepository())
            }.also { instance = it }
    }
}