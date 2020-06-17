package com.darmajayamlearnings.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Mlearningsiswa(
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("kelas_mlearning_id")
    val kelasMlearningId: Int? = 0,
    @SerializedName("mlearningkelas")
    val mlearningkelas: Mlearningkelas? = Mlearningkelas(),
    @SerializedName("siswa_id")
    val siswaId: Int? = 0,
    @SerializedName("updated_at")
    val updatedAt: String? = ""
) : Parcelable