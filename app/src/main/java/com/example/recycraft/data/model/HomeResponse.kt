package com.example.recycraft.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopCraftsModel(
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
    val imageCategory: Int = 0,
    val titleCategory: String? = null,
    val descCategory: String? = null
) : Parcelable

data class CraftResponse(
    @field:SerializedName("data")
    val data: ArrayList<TopCraftsModel>
)