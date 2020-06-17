package com.darmajayamlearnings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.darmajaya.globalsurya.API.ApiClient
import com.darmajayamlearnings.Adapter.KomentarAdapter
import com.darmajayamlearnings.Adapter.MateriAdapter
import com.darmajayamlearnings.Model.Komentar
import com.darmajayamlearnings.Model.Materi
import com.darmajayamlearnings.Model.RJoinKelas
import kotlinx.android.synthetic.main.activity_kelas.*
import kotlinx.android.synthetic.main.activity_kelas.recyclerView
import kotlinx.android.synthetic.main.activity_komentar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.custom_dialog.view.cancel
import kotlinx.android.synthetic.main.custom_dialog.view.submit
import kotlinx.android.synthetic.main.custom_komentar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class KomentarActivity : BaseActivity() {
    var komentar : Komentar? = null
    var responseData :RJoinKelas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komentar)
        initkomentar()

        addkomentar.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_komentar, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Tambah Komentar")
            //show dialog
            val  mAlertDialog = mBuilder.show()

            //login button click of custom layout
            mDialogView.submit.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                //set the input text in TextView
                addkomentar(mDialogView.komentar.text.toString())
            }
//            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
    }

    private fun initkomentar(){
        showLoadingProgress()
        val call: Call<Komentar> = ApiClient.getClient.listkomentar(retrieveToken().toString(), getIntent().getStringExtra("id"))
        call.enqueue(object : Callback<Komentar> {
            override fun onResponse(call: Call<Komentar>, response: Response<Komentar>) {
                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        komentar = response.body()!!
                        recyclerView.adapter = KomentarAdapter(komentar!!.data, applicationContext)
                        recyclerView.layoutManager= LinearLayoutManager(applicationContext,
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

            override fun onFailure(call: Call<Komentar>?, t: Throwable?) {
                dismissLoadingProgress()
                showErrorMessage("Gagal mengambil data")
            }

        })
    }

    private fun addkomentar(komentar: String){
        showLoadingProgress()
        val call: Call<RJoinKelas> = ApiClient.getClient.addKomentar(retrieveToken().toString(), retrieveID(), getIntent().getStringExtra("id"), komentar)
        call.enqueue(object : Callback<RJoinKelas> {
            override fun onResponse(call: Call<RJoinKelas>, response: Response<RJoinKelas>) {
                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        responseData = response.body()!!
                        showSuccessMessage("Komentar berhasil ditambahkan")
                        initkomentar()
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

            override fun onFailure(call: Call<RJoinKelas>?, t: Throwable?) {
                dismissLoadingProgress()
                showErrorMessage("Gagal mengambil data")
            }

        })
    }


}