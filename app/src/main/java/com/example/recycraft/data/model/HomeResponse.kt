package com.example.recycraft.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CraftsModel(
    val id: Int,
    val slug: String,

    @field:SerializedName("scrap_category")
    val categoryCraft: CategoriesModel,

    @field:SerializedName("title")
    val titleCraft: String?,

    @field:SerializedName("image")
    val imageCraft: String?,

    @field:SerializedName("desc")
    val descCraft: String,

    val materials: String,

    val process: String?

) : Parcelable

@Parcelize
data class CategoriesModel(
    val id: Int,

    val slug: String,
    @field:SerializedName("name")
    val titleCategory: String?,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("image")
    val imageCategory: String,

    @field:SerializedName("desc")
    val descCategory: String?
) : Parcelable

data class CraftResponse(
    @field:SerializedName("data")
    val dataCraft: ArrayList<CraftsModel>
)

data class CategoryResponse(
    @field:SerializedName("data")
    val dataCategory: ArrayList<CategoriesModel>
)

