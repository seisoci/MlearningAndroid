package com.darmajayamlearnings.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Matapelajaran(
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("nama_matapelajaran")
    val namaMatapelajaran: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = ""
) : Parcelable

