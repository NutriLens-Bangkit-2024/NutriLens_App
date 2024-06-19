package com.capstone.nutrilens.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection
import com.capstone.nutrilens.data.session.UserRepository

class UserSessionViewModelFactory (private val repository: UserRepository):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserSessionViewModel::class.java)) {
            return UserSessionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserSessionViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): UserSessionViewModelFactory {
            if (INSTANCE == null) {
                synchronized(UserSessionViewModelFactory::class.java) {
                    INSTANCE = UserSessionViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as UserSessionViewModelFactory
        }
    }
}