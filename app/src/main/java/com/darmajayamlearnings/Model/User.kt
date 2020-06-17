package com.darmajayamlearnings.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class User(
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("kelas")
    val kelas: String = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("nis")
    val nis: String? = "",
    @SerializedName("role")
    val role: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = ""
) : Parcelable