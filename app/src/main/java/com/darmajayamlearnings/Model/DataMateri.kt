package com.darmajayamlearnings.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DataMateri(
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("deskripsi")
    val deskripsi: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("file")
    val file: String? = "",
    @SerializedName("judul")
    val judul: String? = "",
    @SerializedName("kelas_mlearning_id")
    val kelasMlearningId: Int? = 0,
    @SerializedName("updated_at")
    val updatedAt: String? = ""
) : Parcelable

