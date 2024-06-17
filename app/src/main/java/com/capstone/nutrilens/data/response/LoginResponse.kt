package com.capstone.nutrilens.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("token")
    val token: String? = null
) : Parcelable

