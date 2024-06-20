package com.capstone.nutrilens.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.response.RecipeResponse
import com.capstone.nutrilens.data.response.RecipesItem
import com.capstone.nutrilens.data.util.Result
import com.google.gson.Gson
import retrofit2.HttpException

class RecipeRepository private constructor(
    private val apiConfig: ApiConfig
){
    private val _recipeData = MutableLiveData<List<RecipesItem>>()
    val recipeData : LiveData<List<RecipesItem>> = _recipeData

    fun getRecipes(token:String) = liveData {
        emit(com.capstone.nutrilens.data.util.Result.Loading)
        try {
            val successResponse = apiConfig.getRecipes("Bearer $token")
            _recipeData.postValue(successResponse.data.recipes)
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RecipeResponse::class.java)
            emit(Result.Error(errorResponse.status))
        }
    }

    companion object{
        @Volatile
        private var instance : RecipeRepository? = null
        fun getInstance(apiConfig: ApiConfig): RecipeRepository =
            instance?: synchronized(this){
                instance?: RecipeRepository(apiConfig)
            }.also { instance = it }
    }
}