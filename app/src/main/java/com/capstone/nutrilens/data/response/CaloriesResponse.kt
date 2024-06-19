package com.capstone.nutrilens.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaloriesResponse(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: CaloriesData? = null
) : Parcelable

@Parcelize
data class CaloriesData(
    @field:SerializedName("dailyCalories")
    val dailyCalories: Map<String, Int>? = null,

    @field:SerializedName("totalCalories")
    val totalCalories: Int? = null
) : Parcelable
