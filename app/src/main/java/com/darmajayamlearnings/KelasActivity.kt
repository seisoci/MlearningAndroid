package com.darmajayamlearnings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.darmajayamlearnings.API.ApiClient
import com.darmajayamlearnings.Adapter.KelasAdapter
import com.darmajayamlearnings.Adapter.MateriAdapter
import com.darmajayamlearnings.Model.*
import kotlinx.android.synthetic.main.activity_kelas.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class KelasActivity : BaseActivity() {
    var kelas :Kelas? = null
    var rJoinKelas :RJoinKelas? = null
    var tambahKelas: TambahKelas? = null
    var id_kelas: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelas)
        setSupportActionBar(findViewById(R.id.toolbar))

        initkelas()
        initDataSpinner()
        addkelas.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Tambah Kelas")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            val listSpinner = ArrayList<String?>()
            val listId = ArrayList<String?>()

            tambahKelas!!.data!!.forEachIndexed { index, element ->
                listSpinner.add(tambahKelas!!.data!!.get(index).kelas!!.namaKelas + " | " +tambahKelas!!.data!!.get(index).matapelajaran!!.namaMatapelajaran  + " | " +tambahKelas!!.data!!.get(index).user!!.name  + " | " +tambahKelas!!.data!!.get(index).tahun + " | " +tambahKelas!!.data!!.get(index).jurusan)
                listId.add(tambahKelas!!.data!!.get(index).id.toString())
            }

            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listSpinner
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                mDialogView.spinner.adapter = adapter
                val bundle: Bundle? = intent.extras
                bundle?.let {
                    bundle.apply {
//                        val pegawai: DetailPekerja? = getParcelable("dataPegawai")
                        /*  if (pegawai != null) {
                              mDialogView.spinner.setSelection(pegawai.idKriteria!!.toInt()-1)

                          }*/
                    }
                }
            }

            mDialogView.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    //text_view.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                    id_kelas = listId.get(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Another interface callback
                }
            }


            //login button click of custom layout
            mDialogView.submit.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                //set the input text in TextView
                joinkelas(id_kelas.toString())
            }
//            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
    }

    private fun initkelas() {
        showLoadingProgress()
        val call: Call<Kelas> = ApiClient.getClient.userkelas(retrieveToken().toString())
        call.enqueue(object : Callback<Kelas> {

            override fun onResponse(call: Call<Kelas>, response: Response<Kelas>) {

                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        kelas = response.body()!!
                        recyclerView.adapter = KelasAdapter(kelas!!.data!!.mlearningsiswa,this@KelasActivity)
                        recyclerView.layoutManager= LinearLayoutManager(this@KelasActivity,
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

            override fun onFailure(call: Call<Kelas>?, t: Throwable?) {
                dismissLoadingProgress()
                showErrorMessage("Gagal mengambil data")
            }

        })
    }

    private fun initDataSpinner(){
        val call: Call<TambahKelas> = ApiClient.getClient.listKelasTersedia(retrieveToken().toString())
        call.enqueue(object : Callback<TambahKelas> {
            override fun onResponse(call: Call<TambahKelas>, response: Response<TambahKelas>) {

                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        tambahKelas = response.body()!!

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

            override fun onFailure(call: Call<TambahKelas>?, t: Throwable?) {
                dismissLoadingProgress()
                showErrorMessage("Gagal mengambil data")
            }

        })

    }

    private fun joinkelas(id_kelas: String){
        showLoadingProgress()
        val call: Call<RJoinKelas> =
            ApiClient.getClient.joinKelas(retrieveToken().toString(), id_kelas, retrieveID())
        call.enqueue(object : Callback<RJoinKelas> {

            override fun onResponse(call: Call<RJoinKelas>?, response: Response<RJoinKelas>?) {
                dismissLoadingProgress()
                when {
                    response?.isSuccessful!! -> {
                        rJoinKelas = response.body()
                        if(rJoinKelas!!.data == "Data has been saved"){
                            dismissLoadingProgress()
                            showSuccessMessage("Berhasil Ikut Kelas")
                            initkelas()
                        }else{
                            dismissLoadingProgress()
                            showErrorMessage("Kelas sudah diikuti")
                        }

                    }
                    response.code() == 500 -> {
                        dismissLoadingProgress()
                        showErrorMessage("Gagal menyimpan data")

                    }
                    else -> {
                        dismissLoadingProgress()
                        showErrorMessage("Gagal menyimpan data")

                    }
                }

            }

            override fun onFailure(call: Call<RJoinKelas>?, t: Throwable?) {
                showErrorMessage("Pastikan koneksi terhubung internet")
                dismissLoadingProgress()

            }

        })
    }


}