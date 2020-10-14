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
import com.darmajayamlearnings.Model.RUploadTugas
import kotlinx.android.synthetic.main.activity_upload_tugas.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap


class UploadTugasActivity : BaseActivity() {
    private var dataMateri: DataMateri? = null
    private var idMateri: String? = null
    private val PERMISSION_CODE = 1000
    var encodedPDF: String? = null
    private val PICK_PDF_REQUEST = 2000
    var filePath: Uri? = null
    var inpusstream: InputStream? = null
    var inpustreamdata: ByteArray? = null
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
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type ="application/pdf"
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 0)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data) {
            filePath = data.data!!
            files_text.text = "File siap untuk di upload"

            inpusstream = getContentResolver().openInputStream(filePath!!)!!
            inpustreamdata = getBytes(inpusstream!!)
        }
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 0)
    }

    private fun uploadFile() {
        showLoadingProgress()
        if (filePath != null) {
            var file = File(filePath!!.path)
            showLoadingProgress()
            val map = HashMap<String, RequestBody>()
            map.put("mlearningmateri_id", createRequestBody(idMateri.toString()))
            map.put("user_id", createRequestBody(retrieveID().toString()))
            val requestBody1: RequestBody = RequestBody.create(
                "application/pdf".toMediaTypeOrNull(),
                inpustreamdata!!
            )

            val fileToUpload =
                MultipartBody.Part.createFormData("file", file.name, requestBody1)
            val call: Call<RUploadTugas> =
                ApiClient.getClient.uploadTugas(retrieveToken().toString(), fileToUpload, map)
            call.enqueue(object : Callback<RUploadTugas> {
                override fun onResponse(call: Call<RUploadTugas>?, response: Response<RUploadTugas>?) {
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

                override fun onFailure(call: Call<RUploadTugas>?, t: Throwable?) {
                    showErrorMessage("Upload gagal")
                    dismissLoadingProgress()

                }

            })
        } else {
            showErrorMessage("file harus di masukan")
            dismissLoadingProgress()

        }
    }

    @Throws(IOException::class)
    private fun getBytes(`is`: InputStream): ByteArray? {
        val byteBuff = ByteArrayOutputStream()
        val buffSize = 1024
        val buff = ByteArray(buffSize)
        var len = 0
        while (`is`.read(buff).also({ len = it }) != -1) {
            byteBuff.write(buff, 0, len)
        }
        return byteBuff.toByteArray()
    }
}