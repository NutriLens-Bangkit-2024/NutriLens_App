package com.capstone.nutrilens.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaloriesResponse(
    @field:SerializedName("weeklyCalories")
    val weeklyCalories: Int? = null
) : Parcelable

