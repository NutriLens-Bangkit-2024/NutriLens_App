package com.capstone.nutrilens.data.response

import com.google.gson.annotations.SerializedName

data class ModelTestingResponse(

	@field:SerializedName("score")
	val score: String,

	@field:SerializedName("cal_per_100g")
	val calPer100g: Int
)
