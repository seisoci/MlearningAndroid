package com.darmajayamlearnings.Model
import android.annotation.SuppressLint
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

import com.google.gson.annotations.SerializedName


@SuppressLint("ParcelCreator")
@Parcelize
data class Komentar(
    @SerializedName("data")
    val `data`: List<DataKomentar>? = listOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("success")
    val success: Boolean? = false
) : Parcelable

