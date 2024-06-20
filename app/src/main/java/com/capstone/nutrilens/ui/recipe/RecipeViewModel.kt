package com.capstone.nutrilens.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.nutrilens.data.response.RecipesItem

class RecipeViewModel (private val recipeRepository:RecipeRepository) : ViewModel() {

    fun getRecipes(token :String) = recipeRepository.getRecipes(token)
    fun recipeData (): LiveData<List<RecipesItem>> = recipeRepository.recipeData
}