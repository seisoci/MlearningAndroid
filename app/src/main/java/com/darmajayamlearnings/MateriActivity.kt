package com.darmajayamlearnings

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.darmajayamlearnings.API.ApiClient
import com.darmajayamlearnings.Adapter.KelasAdapter
import com.darmajayamlearnings.Adapter.MateriAdapter
import com.darmajayamlearnings.Model.Kelas
import com.darmajayamlearnings.Model.Materi
import kotlinx.android.synthetic.main.activity_kelas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateriActivity : BaseActivity()  {
    var materi : Materi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi)
        setSupportActionBar(findViewById(R.id.toolbar))
//        println("test 1" + getIntent().getStringExtra("id"))
        initmateri()
    }

    private fun initmateri() {
        showLoadingProgress()
        val call: Call<Materi> = ApiClient.getClient.listmateri(retrieveToken().toString(), getIntent().getStringExtra("id"))
        call.enqueue(object : Callback<Materi> {
            override fun onResponse(call: Call<Materi>, response: Response<Materi>) {
                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        materi = response.body()!!
                        recyclerView.adapter = MateriAdapter(materi!!.data,this@MateriActivity, retrieveToken()!!, retrieveID()!!, getIntent().getStringExtra("id"))
                        recyclerView.layoutManager= LinearLayoutManager(this@MateriActivity,
                            LinearLayoutManager.VERTICAL,false)
                    }
                    response.code() == 500 -> {
                        dismissLoadingProgress()
                        showErrorMessage("Gagal mengambil data")

                    }
                    else -> {
                        dismissLoadingProgress()
                        showErrorMessage("Gagal mengambil data")

                    }
                }
            }

            override fun onFailure(call: Call<Materi>?, t: Throwable?) {
                dismissLoadingProgress()
                showErrorMessage("Gagal mengambil data")
            }

        })
    }
}