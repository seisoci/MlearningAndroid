package com.darmajayamlearnings

import android.content.Intent
import android.os.Bundle
import com.darmajayamlearnings.API.ApiClient
import com.darmajayamlearnings.Model.Auth
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        submit.setOnClickListener {
            initlogin()
        }

        sign_up.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            this.startActivity(i)
        }
    }

    private fun initlogin() {
        showLoadingProgress()
        val call: Call<Auth> =
            ApiClient.getClient.signIn(email.text.toString(), password.text.toString())
        call.enqueue(object : Callback<Auth> {

            override fun onResponse(call: Call<Auth>?, response: Response<Auth>?) {
                showSuccessMessage("Login Sukses")
                dismissLoadingProgress()
                when {
                    response?.isSuccessful!! -> {
                        dismissLoadingProgress()
                        showSuccessMessage("Login Sukses")
                        tokenAdded(response!!.body()!!)
                    }
                    response.code() == 500 -> {
                        dismissLoadingProgress()
                        showErrorMessage("Email / Password Salah")

                    }
                    else -> {
                        dismissLoadingProgress()
                        showErrorMessage("Email / Password Salah")

                    }
                }

            }

            override fun onFailure(call: Call<Auth>?, t: Throwable?) {
                showErrorMessage("NIK / Password Salah")
                dismissLoadingProgress()

            }

        })
    }

    override fun onResume() {
        super.onResume()
        checkAuth()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoadingProgress()
    }
}