package com.capstone.nutrilens.data.di

import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.ui.recipe.RecipeRepository
import com.capstone.nutrilens.ui.register.RegisterRepository

object Injection {
    fun provideRecipeRepository(): RecipeRepository {
        val apiconfig = ApiService.instanceRetrofit
        return RecipeRepository.getInstance(apiconfig)
    }

    fun provideRegisterRepository(): RegisterRepository {
        val apiconfig = ApiService.instanceRetrofit
        return RegisterRepository.getInstance(apiconfig)
    }
}