package com.darmajayamlearnings.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darmajayamlearnings.*
import com.darmajayamlearnings.API.ApiClient
import com.darmajayamlearnings.Model.*
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_kelas.*
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateriAdapter(private var dataList: List<DataMateri>?, private val context: Context, private val token: String, private val user_id: String, private val mlearningkelas_id: String) :
    RecyclerView.Adapter<MateriAdapter.ViewHolder>() {
    lateinit var loading: AlertDialog


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_materi,
                parent,
                false
            )
        )
        val dialog = AlertDialog.Builder(context)
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.loading, null)
        dialog.setView(mDialogView)
        dialog.setCancelable(false)
        loading = dialog.create()
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataList!!.get(position)
/*        val mySharedPreference = MySharedPreference(context)
        holder.tahun.text = dataModel.tahun.toString()
        holder.pdfganjil.setOnClickListener {
            val url =  ApiClient.BASE_URL +"raport/downloadPDF/${dataModel.tahun}/${mySharedPreference.retriveID().toString()}/ganjil"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        }*/
        holder.tanggal.text = dataModel.createdAt.toString()
        holder.judul.text = dataModel.judul.toString()
        Glide.with(context)
            .load(ApiClient.BASE_URL +"storage/images/original/"+ dataModel.image)
            .fitCenter()
            .into(holder.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailMateri::class.java)
            intent.putExtra("data", dataModel)
            context.startActivity(intent)
        }
        holder.komentar.setOnClickListener {
            val intent = Intent(context, KomentarActivity::class.java)
            intent.putExtra("id", dataModel.id.toString())
            context.startActivity(intent)
        }
        holder.upload.setOnClickListener {
            val intent = Intent(context, UploadTugasActivity::class.java)
            intent.putExtra("id", dataModel.id.toString())
            context.startActivity(intent)
        }
        holder.absensi.setOnClickListener {
            val call: Call<RAbsensi> = ApiClient.getClient.addAbsensi(token, user_id, mlearningkelas_id, dataModel.id.toString())
            call.enqueue(object : Callback<RAbsensi> {
                override fun onResponse(call: Call<RAbsensi>, response: Response<RAbsensi>) {
                    when {
                        response?.isSuccessful!! -> {
                            StyleableToast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG, R.style.sukses).show()
                        }
                        response.code() == 500 -> {
                            StyleableToast.makeText(context, "gagal absensi", Toast.LENGTH_LONG, R.style.sukses).show()


                        }
                        else -> {
                            StyleableToast.makeText(context, "gagal absensi", Toast.LENGTH_LONG, R.style.sukses).show()


                        }
                    }
                }

                override fun onFailure(call: Call<RAbsensi>?, t: Throwable?) {
                    StyleableToast.makeText(context, "gagal absensi", Toast.LENGTH_LONG, R.style.sukses).show()

                }

            })
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var judul: TextView
        lateinit var image: ImageView
        lateinit var komentar: TextView
        lateinit var tanggal: TextView
        lateinit var absensi: TextView
        lateinit var upload: TextView


        init {
            judul = view.findViewById(R.id.judul)
            image = view.findViewById(R.id.image)
            tanggal = view.findViewById(R.id.tanggal)
            komentar = view.findViewById(R.id.komentar)
            absensi = view.findViewById(R.id.absensi)
            upload = view.findViewById(R.id.upload)

        }

    }

}
