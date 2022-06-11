package com.example.recycraft.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @field:SerializedName("data")
    val listCategory: List<ListCategory>
)

data class ListCategory (
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("image")
    val image: String,
    @field:SerializedName("desc")
    val desc: String
)


