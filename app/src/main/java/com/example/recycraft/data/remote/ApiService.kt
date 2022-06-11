package com.example.recycraft.data.remote

import com.example.recycraft.data.model.*
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

    @GET("handicraft")
    fun getAllCraft(): Call<CraftResponse>

    @GET("handicraft/{slug}")
    fun getCraftBySlug(
        @Path("slug") slug: String
    ): Call<CraftsModel>

    @GET("category")
    fun getAllCategory(): Call<CategoryResponse>

    @GET("category/{slug}")
    fun getCategoryBySlug(
        @Path("slug") slug: String
    ): Call<CategoriesModel>


}