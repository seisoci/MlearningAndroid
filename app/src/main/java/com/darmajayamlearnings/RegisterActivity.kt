package com.darmajayamlearnings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.darmajayamlearnings.API.ApiClient
import com.darmajayamlearnings.Model.Auth
import com.darmajayamlearnings.Model.RKelas
import com.darmajayamlearnings.Utils.Validate
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.forEachIndexed

class RegisterActivity : BaseActivity() {
    var mediaColumns =
        arrayOf(MediaStore.Video.Media._ID)
    var mediaPath: String? = null
    var mediaPath1: String? = null
    private val PERMISSION_CODE = 1000
    var rKelas: RKelas? = null
    var id_kelas: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initDataSpinner()

        btnCreateAccount.setOnClickListener {
            daftar()
        }

        image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    val galleryIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(galleryIntent, 0)
                }
            } else {
                //system os is < marshmallow
                val galleryIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(galleryIntent, 0)
            }
        }
    }

    private fun daftar() {
//        if (!Validate.ceklogin(email) && Validate.ceklogin(password) && !Validate.ceklogin(name)) {
            if(mediaPath != null) {
                showLoadingProgress()
                val file = File(mediaPath)

                val map = HashMap<String, RequestBody>()
                map.put("name", createRequestBody(name.text.toString()))
                map.put("email", createRequestBody(email.text.toString()))
                map.put("nis", createRequestBody(nis.text.toString()))
                map.put("password", createRequestBody(password.text.toString()))
                map.put("kelas", createRequestBody(spinnerkelas.selectedItem.toString()))
                val requestBody1: RequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), file)

                val fileToUpload =
                    MultipartBody.Part.createFormData("image", file.name, requestBody1)
                val call: Call<Auth> =
                    ApiClient.getClient.register(fileToUpload, map)
                call.enqueue(object : Callback<Auth> {
                    override fun onResponse(call: Call<Auth>?, response: Response<Auth>?) {
                        dismissLoadingProgress()
                        when {
                            response?.isSuccessful!! -> {
                                if (response!!.body()!!.success!!) {
                                    dismissLoadingProgress()
                                    showSuccessMessage("Buat Akun Sukses")
                                    finish()
                                } else {
                                    showErrorMessage("Pastikan Email/NIK belum terdaftar")
                                }

                            }
                            response.code() == 500 -> {
                                dismissLoadingProgress()
                                showErrorMessage("Pastikan Email/NIK belum terdaftar")

                            }
                            else -> {
                                dismissLoadingProgress()
                                showErrorMessage("Register Gagal")

                            }
                        }

                    }

                    override fun onFailure(call: Call<Auth>?, t: Throwable?) {
                        showErrorMessage("Register Gagal 2")
                        dismissLoadingProgress()

                    }

                })
            }else{
                showErrorMessage("Photo harus di masukan")
//            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data) {

                // Get the Image from data
                val selectedImage: Uri? = data.data
                val filePathColumn =
                    arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    selectedImage?.let {
                        contentResolver.query(
                            it,
                            filePathColumn,
                            null,
                            null,
                            null
                        )
                    }!!
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                mediaPath = cursor.getString(columnIndex)
//                str1.setText(mediaPath)
                // Set the Image in ImageView for Previewing the Media
                image.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                cursor.close()
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        }
    }

    // Providing Thumbnail For Selected Image
    fun getThumbnailPathForLocalFile(context: Activity, fileUri: Uri?): Bitmap? {
        val fileId = getFileId(context, fileUri)
        return MediaStore.Video.Thumbnails.getThumbnail(
            context.contentResolver,
            fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null
        )
    }

    // Getting Selected File ID
    fun getFileId(context: Activity, fileUri: Uri?): Long {
        val cursor =
            context.managedQuery(fileUri, mediaColumns, null, null, null)
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            return cursor.getInt(columnIndex).toLong()
        }
        return 0
    }

    private fun initDataSpinner() {
        showLoadingProgress()
        val call: Call<RKelas> = ApiClient.getClient.listkelas()
        call.enqueue(object : Callback<RKelas> {
            override fun onResponse(call: Call<RKelas>, response: Response<RKelas>) {

                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        rKelas = response.body()!!
                        initspinner(rKelas!!)
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

            override fun onFailure(call: Call<RKelas>?, t: Throwable?) {
                dismissLoadingProgress()
                showErrorMessage("Gagal mengambil data")
            }

        })

    }

    private fun initspinner(data: RKelas) {
        val listSpinner = ArrayList<String?>()
        val listId = ArrayList<String?>()

        data!!.data!!.forEachIndexed { index, element ->
            listSpinner.add(data!!.data!!.get(index)!!.namaKelas)
            listId.add(data!!.data!!.get(index).id.toString())
        }

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listSpinner
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerkelas.adapter = adapter
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

        spinnerkelas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    }


}