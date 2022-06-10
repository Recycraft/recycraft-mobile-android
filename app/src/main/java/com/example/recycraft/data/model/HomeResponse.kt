package com.example.recycraft.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopCraftsModel(
    var imageCraft: Int = 0,
    var titleCraft: String? = null,
    var descCraft: String? = null,
    var categoryCraft: String? = null

) : Parcelable

@Parcelize
data class CategoriesModel(
    var imageCategory: Int = 0,
    var titleCategory: String? = null,
    var descCategory: String? = null
) : Parcelable