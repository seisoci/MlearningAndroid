package com.darmajayamlearnings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.darmajayamlearnings.API.ApiClient
import com.darmajayamlearnings.API.ApiClient.BASE_URL
import com.darmajayamlearnings.Model.Auth
import com.darmajayamlearnings.Model.DataMateri
import com.darmajayamlearnings.Model.RJoinKelas
import com.darmajayamlearnings.Model.RUpdateStatus
import kotlinx.android.synthetic.main.activity_detail_materi.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMateri : BaseActivity() {
    private var dataMateri: DataMateri? = null
    private var idMateri: String? = null
    private var urlMateri: String? = null

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
                dataMateri?.judul?.let {
                    judul.setText(dataMateri!!.judul)
                }
                dataMateri?.deskripsi?.let {
                    deskripsi.setText(dataMateri!!.deskripsi)
                }
                idMateri = dataMateri!!.id.toString()
                urlMateri = dataMateri!!.file.toString()
            }
        }

        download.setOnClickListener {
            initData(idMateri!!, urlMateri!!)
        }
    }

    private fun initData(idMateri :String, urlMateri :String) {
        showLoadingProgress()
        val call: Call<RUpdateStatus> =
            ApiClient.getClient.updateStatus(retrieveToken()!!, retrieveID()!!, idMateri)
        call.enqueue(object : Callback<RUpdateStatus> {

            override fun onResponse(call: Call<RUpdateStatus>?, response: Response<RUpdateStatus>?) {
                dismissLoadingProgress()
                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        val url =  BASE_URL +"storage/document/${urlMateri}"
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    }
                    response.code() == 500 -> {
                        showSuccessMessage("Gagal mengambil data")
                        dismissLoadingProgress()

                    }
                    else -> {
                        showSuccessMessage("Gagal mengambil data")
                        dismissLoadingProgress()
                    }
                }

            }

            override fun onFailure(call: Call<RUpdateStatus>?, t: Throwable?) {
                dismissLoadingProgress()
            }

        })
    }
}