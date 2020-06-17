package com.darmajayamlearnings.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darmajaya.globalsurya.API.ApiClient
import com.darmajayamlearnings.DetailMateri
import com.darmajayamlearnings.KomentarActivity
import com.darmajayamlearnings.MateriActivity
import com.darmajayamlearnings.Model.DataMateri
import com.darmajayamlearnings.Model.Materi
import com.darmajayamlearnings.Model.Mlearningsiswa
import com.darmajayamlearnings.R
import kotlinx.android.synthetic.main.activity_profile.*

class MateriAdapter(private var dataList: List<DataMateri>?, private val context: Context) :
    RecyclerView.Adapter<MateriAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_materi,
                parent,
                false
            )
        )
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
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var judul: TextView
        lateinit var image: ImageView
        lateinit var komentar: TextView
        lateinit var tanggal: TextView

        init {
            judul = view.findViewById(R.id.judul)
            image = view.findViewById(R.id.image)
            tanggal = view.findViewById(R.id.tanggal)
            komentar = view.findViewById(R.id.komentar)

        }

    }

}
