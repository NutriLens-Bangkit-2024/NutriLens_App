package com.capstone.nutrilens.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.di.Injection
import com.capstone.nutrilens.ui.recipe.RecipeRepository
import com.capstone.nutrilens.ui.recipe.RecipeViewModel

//class LoginViewModelFactory(private val recipeRepository: LoginRepository) : ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(RecipeViewModel::class.java)){
//            return LoginViewModel(loginRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//
//    companion object{
//        @Volatile
//        private var instance : LoginViewModelFactory?=null
//        fun getInstance(context: Context):LoginViewModelFactory=
//            instance?: synchronized(this){
//                instance?: LoginViewModelFactory(Injection.provideLoginRepository())
//            }.also { instance = it }
//    }
//}