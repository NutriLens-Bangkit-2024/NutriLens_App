package com.capstone.nutrilens.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: UserData? = null
) : Parcelable

@Parcelize
data class UserData(
    @field:SerializedName("user")
    val user: User? = null
) : Parcelable

@Parcelize
data class User(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("profileurl")
    val profileurl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) : Parcelable


