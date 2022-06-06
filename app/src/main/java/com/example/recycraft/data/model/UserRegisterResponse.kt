package com.example.recycraft.data.model

import com.google.gson.annotations.SerializedName

data class UserRegisterResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)