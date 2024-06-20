package com.capstone.nutrilens.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EditUserResponse(
	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("message")
	val message: String? = null,
) : Parcelable
