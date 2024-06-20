package com.capstone.nutrilens.data.di

import android.content.Context
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.session.UserPreference
import com.capstone.nutrilens.data.session.UserRepository
import com.capstone.nutrilens.data.session.dataStore
import com.capstone.nutrilens.ui.camera.model.ModelRepository
import com.capstone.nutrilens.ui.camera.predict.PredictionRepository
import com.capstone.nutrilens.ui.camera.scanning.ScanningRepository
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

    fun provideProfileRepository(): ProfileRepository {
        val apiconfig = ApiService.instanceRetrofit
        return ProfileRepository.getInstance(apiconfig)
    }

    fun provideChangeProfileRepository(): ChangeProfileRepository {
        val apiconfig = ApiService.instanceRetrofit
        return ChangeProfileRepository.getInstance(apiconfig)
    }

    fun provideScanningRepository(): ScanningRepository {
        val apiconfig = ApiService.scanningRetrofit
        return ScanningRepository.getInstance(apiconfig)
    }

    fun provideModelRepository():ModelRepository{
        val apiconfig = ApiService.modelRetrofit
        return ModelRepository.getInstance(apiconfig)
    }

    fun providePredictionRepository(): PredictionRepository {
        val apiconfig = ApiService.instanceRetrofit
        return PredictionRepository.getInstance(apiconfig)
    }

    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}