package com.capstone.nutrilens.data.di

import com.capstone.nutrilens.data.api.ApiService

object Injection {
    fun provideRecipeRepository(): RecipeRepository {
        val apiconfig = ApiService.instanceRetrofit
        return RecipeRepository.getInstance(apiconfig)
    }

//    fun provideRegisterRepository(): RegisterRepository {
//        val apiconfig = ApiService.instanceRetrofit
//        return RegisterRepository.getInstance(apiconfig)
//    }
}