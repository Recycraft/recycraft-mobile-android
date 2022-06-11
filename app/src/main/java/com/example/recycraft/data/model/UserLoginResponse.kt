package com.example.recycraft.data.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @field:SerializedName("data")
    val dataLogin: LoginModel

)

data class LoginModel(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val userId: Int,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String
)
