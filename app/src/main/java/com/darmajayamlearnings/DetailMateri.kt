package com.darmajayamlearnings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.darmajaya.globalsurya.API.ApiClient.BASE_URL
import com.darmajayamlearnings.Model.DataMateri
import kotlinx.android.synthetic.main.activity_detail_materi.*

class DetailMateri : AppCompatActivity() {
    private var dataMateri: DataMateri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                dataMateri = getParcelable("data")
                Glide.with(applicationContext)
                    .load(BASE_URL + "storage/images/original/" + dataMateri!!.image)
                    .centerCrop()
                    .into(image)
                Glide.with(applicationContext)
                    .load(BASE_URL + "storage/images/original/" + dataMateri!!.image2)
                    .centerCrop()
                    .into(image2)
                Glide.with(applicationContext)
                    .load(BASE_URL + "storage/images/original/" + dataMateri!!.image3)
                    .centerCrop()
                    .into(image3)
                dataMateri?.judul?.let {
                    judul.setText(dataMateri!!.judul)
                }
                dataMateri?.deskripsi?.let {
                    deskripsi.setText(dataMateri!!.deskripsi)
                }
            }
        }
    }
}