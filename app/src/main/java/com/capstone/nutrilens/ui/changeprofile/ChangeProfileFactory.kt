package com.capstone.nutrilens.ui.changeprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection

class ChangeProfileFactory(private val changeProfileRepository: ChangeProfileRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChangeProfileViewModel(changeProfileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        @Volatile
        private var instance : ChangeProfileFactory?=null
        fun getInstance(context: ChangeProfileRepository): ChangeProfileFactory =
            instance?: synchronized(this){
                instance?: ChangeProfileFactory(Injection.provideChangeProfileRepository())
            }.also { instance = it }
    }
}