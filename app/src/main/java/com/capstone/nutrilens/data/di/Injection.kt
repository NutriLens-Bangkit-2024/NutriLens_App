package com.capstone.nutrilens.data.di

import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.ui.camera.ScanningRepository
import com.capstone.nutrilens.ui.changeprofile.ChangeProfileRepository
import com.capstone.nutrilens.ui.login.LoginRepository
import com.capstone.nutrilens.ui.profile.ProfileRepository
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

    fun provideLoginRepository(): LoginRepository {
        val apiconfig = ApiService.instanceRetrofit
        return LoginRepository.getInstance(apiconfig)
    }

    fun provideProfileRepository(): ProfileRepository {
        val apiconfig = ApiService.instanceRetrofit
        return ProfileRepository.getInstance(apiconfig)
    }

    fun provideChangeProfileRepository(): ChangeProfileRepository {
        val apiconfig = ApiService.instanceRetrofit
        return ChangeProfileRepository.getInstance(apiconfig)
    }

    fun provideScanningRepository(): ScanningRepository{
        val apiconfig = ApiService.scanningRetrofit
        return ScanningRepository.getInstance(apiconfig)
    }
}