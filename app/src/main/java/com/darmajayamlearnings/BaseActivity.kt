package com.darmajayamlearnings


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.darmajayamlearnings.Model.Auth
import com.darmajayamlearnings.Utils.MySharedPreference
import com.muddzdev.styleabletoast.StyleableToast
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody


open class BaseActivity : AppCompatActivity() {

    lateinit var loading: AlertDialog
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialog = AlertDialog.Builder(this)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.loading, null)
        dialog.setView(mDialogView)
        dialog.setCancelable(false)
        loading = dialog.create()

    }


    fun showLoadingProgress() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loading.show()
    }

    fun dismissLoadingProgress() {
        if (loading.isShowing)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loading.dismiss()

    }

    fun showErrorMessage(message: String?) {
        StyleableToast.makeText(this, message.toString(), Toast.LENGTH_LONG, R.style.sukses).show()

    }

    fun showSuccessMessage(message: String?) {
        StyleableToast.makeText(this, message.toString(), Toast.LENGTH_LONG, R.style.cancel).show()
    }


    fun showProgressBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progressBar.visibility = View.VISIBLE
    }


    fun dismissProgressBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressBar.visibility = View.GONE
    }

    fun tokenAdded(data: Auth) {
        val mySharedPreference = MySharedPreference(this)
        println("auth data "+ data.data!!.apiToken)
        mySharedPreference.addToken(data?.data)
        val i = Intent(this, MainActivity::class.java)
        this.startActivity(i)
        this.finish()
    }

    fun tokenExpired() {
        val mySharedPreference = MySharedPreference(this)
        mySharedPreference.destoryPref()
        val i = Intent(this, LoginActivity::class.java)
        this.startActivity(i)
        this.finish()
    }

    fun checkAuth() {
        val mySharedPreference = MySharedPreference(this)
        if (mySharedPreference.hasCachedAccessToken()) {
            val i = Intent(this, MainActivity::class.java)
            this.startActivity(i)
            this.finish()
        }

    }

    fun retrieveToken(): String? {
        val mySharedPreference = MySharedPreference(this)
        return mySharedPreference.retriveToken()
    }

    fun retrieveID(): String? {
        val mySharedPreference = MySharedPreference(this)
        return mySharedPreference.retriveID()
    }

    fun retrieveName(): String? {
        val mySharedPreference = MySharedPreference(this)
        return mySharedPreference.retriveName()
    }

    fun retrieveNIS(): String? {
        val mySharedPreference = MySharedPreference(this)
        return mySharedPreference.retriveNIS()
    }

    fun retrieveKelas(): String? {
        val mySharedPreference = MySharedPreference(this)
        return mySharedPreference.retriveKelas()
    }

    fun retrieveEmail(): String? {
        val mySharedPreference = MySharedPreference(this)
        return mySharedPreference.retriveEmail()
    }

    fun retrieveImage(): String? {
        val mySharedPreference = MySharedPreference(this)
        return mySharedPreference.retriveImage()
    }

    fun createRequestBody(s: String): RequestBody {
        return RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(), s)
    }


}