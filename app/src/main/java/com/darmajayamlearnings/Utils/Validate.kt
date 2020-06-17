package com.darmajayamlearnings.Utils
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

import com.google.android.material.textfield.TextInputEditText


object Validate {

    fun cek(et: EditText): Boolean {
        var focusView: View? = null
        var cancel: Boolean? = false
        if (TextUtils.isEmpty(et.text.toString().trim { it <= ' ' })) {
            et.error = "Inputan Harus Di Isi"
            focusView = et
            cancel = true
        }
        if (cancel!!) {
            focusView!!.requestFocus()
        }
        return cancel
    }

    fun ceklogin(et: EditText): Boolean {
        var focusView: View? = null
        var cancel: Boolean? = false
        if (TextUtils.isEmpty(et.text!!.toString().trim { it <= ' ' })) {
            et.error = "Inputan Harus Di Isi"
            focusView = et
            cancel = true
        }
        if (cancel!!) {
            focusView!!.requestFocus()
        }
        return cancel
    }


    fun cekPassword(et: TextInputEditText, et2: TextInputEditText): Boolean {
        var focusView: View? = null
        var cancel: Boolean? = false
        if (TextUtils.isEmpty(et.text!!.toString().trim { it <= ' ' })) {
            et.error = "Password tidak boleh kosong / < 4 digit"
            focusView = et
            cancel = true
        } else if (TextUtils.isEmpty(et2.text!!.toString().trim { it <= ' ' })) {
            et2.error = "Password tidak boleh kosong / < 4 digit"
            focusView = et2
            cancel = true
        } else if (et.text!!.toString().trim { it <= ' ' } !== et2.text!!.toString().trim { it <= ' ' }) {
            et.error = "Password Tidak sama"
            focusView = et2
            cancel = true
        } else {
            cancel = false
        }

        if (cancel) {
            focusView!!.requestFocus()
        }
        return cancel
    }

    fun minpassword(v: EditText): Boolean {
        var cancel: Boolean? = false
        val pass = v.text.toString()
        if (TextUtils.isEmpty(pass) || pass.length < 8) {
            v.error = "Masukan Password Minimum 8"
            cancel = false
        } else {
            cancel = true
        }

        return cancel

    }

    fun cekemail(v: EditText): Boolean {
        val email = v.text.toString()
        var cancel: Boolean? = false
        if (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            cancel = false
        } else {
            v.error = "Email Tidak Sesuai"
            cancel = true
        }

        return cancel
    }

    fun ceknama(v: EditText): Boolean {
        val pattern = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"

        val nama = v.text.toString()
        var cancel: Boolean? = false
        if (!nama.matches(pattern.toRegex())) {
            v.error = "Nama Tidak Boleh Angka"

            cancel = false
        } else {
            cancel = true
        }

        return cancel
    }

    fun cekrangka(v: EditText): Boolean {
        val rangka = v.text.toString()
        var cancel: Boolean? = false
        if (rangka.length != 17) {
            v.error = "No Rangka Harus 17 Digit"

            cancel = false
        } else {
            cancel = true
        }

        return cancel
    }

    fun cekmesin(v: EditText): Boolean {
        val rangka = v.text.toString()
        var cancel: Boolean? = false
        if (rangka.length < 12) {
            v.error = "No Mesin Minimum 12 Digit"
            cancel = false
        } else {
            cancel = true
        }

        return cancel
    }

    fun cekplat(v: EditText): Boolean {
        val rangka = v.text.toString()
        var cancel: Boolean? = false
        if (rangka.length <= 10) {
            cancel = false
        } else {
            v.error = "No Plat Max 10"
            cancel = true
        }

        return cancel
    }

    fun dismissKeyboard(editText: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
