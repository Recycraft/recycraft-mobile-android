package com.example.recycraft.data.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Session(
    var token: String,
    var isLogin: Boolean
) : Parcelable