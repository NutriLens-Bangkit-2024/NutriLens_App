package com.capstone.nutrilens.data.di

import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.ui.recipe.RecipeRepository

object Injection {
    fun provideRecipeRepository(): RecipeRepository {
        val apiconfig = ApiService.instanceRetrofit
        return RecipeRepository.getInstance(apiconfig)
    }
}