package com.example.recycraft.data.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @field:SerializedName("user")
    val userResult: UserResult

)

data class UserResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val userId: Int,

    @field:SerializedName("token")
    val token: String
)
