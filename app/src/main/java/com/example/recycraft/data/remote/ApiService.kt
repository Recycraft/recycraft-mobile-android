package com.example.recycraft.data.remote

import com.example.recycraft.data.model.UserLoginResponse
import com.example.recycraft.data.model.UserRegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun getLoginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserLoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun createAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserRegisterResponse>
}