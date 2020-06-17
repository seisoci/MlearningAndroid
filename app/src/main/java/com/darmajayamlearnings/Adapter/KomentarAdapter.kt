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
import com.darmajayamlearnings.MateriActivity
import com.darmajayamlearnings.Model.DataKomentar
import com.darmajayamlearnings.Model.Mlearningsiswa
import com.darmajayamlearnings.R
import kotlinx.android.synthetic.main.activity_profile.*

class KomentarAdapter(private var dataList: List<DataKomentar>?, private val context: Context) :
    RecyclerView.Adapter<KomentarAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_komentar,
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

        holder.name.text  = dataModel.user!!.name.toString()
        holder.date.text  = dataModel.createdAt.toString()
        holder.komentar.text  = dataModel.komentar.toString()

        Glide.with(context)
            .load(ApiClient.BASE_URL +"storage/images/original/"+dataModel.user.image)
            .fitCenter()
            .into(holder.image)

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var name: TextView
        lateinit var komentar: TextView
        lateinit var date: TextView
        lateinit var image: ImageView

        init {
            name = view.findViewById(R.id.name)
            komentar = view.findViewById(R.id.komentar)
            date = view.findViewById(R.id.date)
            image = view.findViewById(R.id.image)

        }

    }

}
