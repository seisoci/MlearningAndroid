package com.darmajayamlearnings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import com.darmajayamlearnings.API.ApiClient
import com.darmajayamlearnings.Model.Auth
import com.darmajayamlearnings.Model.DataMateri
import kotlinx.android.synthetic.main.activity_upload_tugas.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class UploadTugasActivity : BaseActivity() {
    private var dataMateri: DataMateri? = null
    private var idMateri: String? = null
    var mediaPath: Uri? = null
    private val PERMISSION_CODE = 1000
    var encodedPDF: String? = null
    var mimeTypes = arrayOf(
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",  // .ppt & .pptx
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  // .xls & .xlsx
        "text/plain",
        "application/pdf",
        "application/zip"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_tugas)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
            }
        }
        idMateri = intent.getStringExtra("id");
        println("sadasdas " + idMateri)


        submit.setOnClickListener {
            uploadFile()
        }

        files.setOnClickListener {
            var mimeTypesStr = ""
            for (mimeType in mimeTypes) {
                mimeTypesStr += "$mimeType|"
            }
            intent.type = if (mimeTypes.size === 1) mimeTypes[0] else "*/*"
            if (mimeTypes.size > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permission, PERMISSION_CODE)
                } else {

                    val intent = Intent()
                    intent.action = Intent.ACTION_OPEN_DOCUMENT
                    intent.setType("application/pdf");
                    startActivityForResult(intent, 0)
                }
            } else {
                val intent = Intent()
                intent.action = Intent.ACTION_OPEN_DOCUMENT
                intent.setType("application/pdf");
                startActivityForResult(intent, 0)
            }
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
                val selectedImage: Uri = data.data!!
                try {
                    val inputStream = this@UploadTugasActivity.contentResolver.openInputStream(
                        selectedImage
                    )
                    val pdfInBytes: ByteArray = ByteArray(inputStream!!.available())
                    inputStream.read(pdfInBytes)
                    encodedPDF = Base64.encodeToString(pdfInBytes, Base64.DEFAULT)
                }catch (e: Exception){
                    e.printStackTrace()
                }

            } else {
                Toast.makeText(this, "You haven't picked Files", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        }
    }
    private fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    private fun uploadFile() {
        showLoadingProgress()
        if (mediaPath != null) {
            showLoadingProgress()
            val file = File(getPath(mediaPath!!))

            val map = HashMap<String, RequestBody>()
            map.put("mlearningmateri_id", createRequestBody(idMateri.toString()))
            map.put("user_id", createRequestBody(retrieveID().toString()))
            val requestBody1: RequestBody = RequestBody.create(
                "application/pdf".toMediaTypeOrNull(),
                file
            )

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
                                showSuccessMessage("Buat berhasil di upload")
                                finish()
                            }
                        }
                        response.code() == 500 -> {
                            dismissLoadingProgress()
                            showErrorMessage("server error")

                        }
                        else -> {
                            dismissLoadingProgress()
                            showErrorMessage("Upload gagal")

                        }
                    }

                }

                override fun onFailure(call: Call<Auth>?, t: Throwable?) {
                    showErrorMessage("Upload gagal")
                    dismissLoadingProgress()

                }

            })
        } else {
            showErrorMessage("file harus di masukan")
            dismissLoadingProgress()

        }
    }
}