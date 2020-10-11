package com.darmajayamlearnings.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Mlearningkelas(
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("tahun")
    val tahun: String? = "",
    @SerializedName("jurusan")
    val jurusan: String? = "",
    @SerializedName("kelas")
    val kelas: MKelas? = MKelas(),
    @SerializedName("matapelajaran")
    val matapelajaran: Matapelajaran? = Matapelajaran(),
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    @SerializedName("user")
    val user: User? = User()
) : Parcelable