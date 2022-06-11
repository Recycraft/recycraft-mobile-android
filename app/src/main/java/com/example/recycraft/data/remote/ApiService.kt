package com.example.recycraft.data.remote

import com.example.recycraft.data.model.CategoryResponse
import com.example.recycraft.data.model.UserLoginResponse
import com.example.recycraft.data.model.UserRegisterResponse
import retrofit2.Call
import retrofit2.http.*
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


    @GET("category")
  //  @Headers("Accept: application/json")
    fun getAllCategory(
        @Field("id") id: Int,
        @Field("slug") slug: String,
        @Field("type") type: String,
        @Field("image") image: String,
        @Field("name") name: String,
        @Field("desc") desc: String
    ): Call<CategoryResponse>


}

