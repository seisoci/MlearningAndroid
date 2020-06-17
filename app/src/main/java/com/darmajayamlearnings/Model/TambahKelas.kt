package com.darmajayamlearnings.Model
import android.annotation.SuppressLint
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

import com.google.gson.annotations.SerializedName


@SuppressLint("ParcelCreator")
@Parcelize
data class TambahKelas(
    @SerializedName("data")
    val `data`: List<DataTambahKelas>? = listOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("success")
    val success: Boolean? = false
) : Parcelable