package com.capstone.nutrilens.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaloriesResponse(
    @field:SerializedName("dailyCalories")
    val dailyCalories: Int? = null,

    @field:SerializedName("weeklyCalories")
    val weeklyCalories: Int? = null,

    @field:SerializedName("totalCalories")
    val totalCalories: Int? = null
) : Parcelable

