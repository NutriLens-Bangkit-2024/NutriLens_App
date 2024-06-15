package com.capstone.nutrilens.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.ui.recipe.RecipeViewModelFactory

class ProfileViewModelFactory(private val profileRepository: ProfileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(profileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        private var instance: ProfileViewModelFactory? = null
        fun getInstance(context: Context): ProfileViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ProfileViewModelFactory(Injection.provideProfileRepository())
            }.also { instance = it }
    }
}