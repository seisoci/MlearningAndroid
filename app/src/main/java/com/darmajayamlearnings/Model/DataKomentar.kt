package com.darmajayamlearnings.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DataKomentar(
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("komentar")
    val komentar: String? = "",
    @SerializedName("materi_mlearning_id")
    val materiMlearningId: Int? = 0,
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    @SerializedName("user")
    val user: User? = User(),
    @SerializedName("users_id")
    val usersId: Int? = 0
) : Parcelable