package com.capstone.nutrilens.data.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("status")
    val status: String
)

data class Data(

    @field:SerializedName("recipes")
    val recipes: List<RecipesItem>
)

data class RecipesItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("ingredient")
    val ingredient: List<String>,

    @field:SerializedName("directions")
    val directions: List<String>,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("calories")
    val calories: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)