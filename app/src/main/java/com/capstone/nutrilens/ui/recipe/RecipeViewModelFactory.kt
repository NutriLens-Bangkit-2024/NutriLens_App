package com.capstone.nutrilens.ui.recipe

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection

class RecipeViewModelFactory(private val recipeRepository: RecipeRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RecipeViewModel::class.java)){
            return RecipeViewModel(recipeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance : RecipeViewModelFactory?=null
        fun getInstance(context: Context):RecipeViewModelFactory=
            instance?: synchronized(this){
                instance?: RecipeViewModelFactory(Injection.provideRecipeRepository())
            }.also { instance = it }
    }
}