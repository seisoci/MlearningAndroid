package com.darmajayamlearnings.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.darmajayamlearnings.MateriActivity
import com.darmajayamlearnings.Model.Mlearningsiswa
import com.darmajayamlearnings.R

class KelasAdapter(private var dataList: List<Mlearningsiswa>?, private val context: Context) :
    RecyclerView.Adapter<KelasAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_kelas,
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
        holder.kelas.text = dataModel.mlearningkelas?.kelas?.namaKelas.toString()
        holder.matapelajaran.text = dataModel.mlearningkelas?.matapelajaran?.namaMatapelajaran.toString()
        holder.guru.text = dataModel.mlearningkelas?.user?.name.toString()
        holder.jurusan.text = dataModel.mlearningkelas?.jurusan
        holder.tahun.text = dataModel.mlearningkelas?.tahun.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MateriActivity::class.java)
            intent.putExtra("id", dataModel.mlearningkelas!!.id.toString())
            context.startActivity(intent)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var tahun: TextView
        lateinit var jurusan: TextView
        lateinit var guru: TextView
        lateinit var kelas: TextView
        lateinit var matapelajaran: TextView

        init {
            tahun = view.findViewById(R.id.tahun)
            jurusan = view.findViewById(R.id.jurusan)
            guru = view.findViewById(R.id.guru)
            kelas = view.findViewById(R.id.kelas)
            matapelajaran = view.findViewById(R.id.matapelajaran)

        }

    }

}
