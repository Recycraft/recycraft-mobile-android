package com.example.recycraft.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLoginResponse(
    @field:SerializedName("user")
    val dataLogin: LoginModel

) : Parcelable

@Parcelize
data class LoginModel(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val userId: Int,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val emailId: String,

    @field:SerializedName("token")
    val token: String
) : Parcelable
