package com.darmajayamlearnings.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class RKelas(

    @SerializedName("data")
    val data: List<MKelas>? = listOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("success")
    val success: Boolean? = false

) : Parcelable