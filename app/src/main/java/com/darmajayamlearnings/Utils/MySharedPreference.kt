package com.darmajayamlearnings.Utils

import android.content.Context
import android.content.SharedPreferences
import com.darmajayamlearnings.Model.DataAuth
import com.darmajayamlearnings.Model.Kelas

class MySharedPreference(context: Context?) {

    private val prefs: SharedPreferences

    init {
        prefs = context!!.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    fun addToken(data: DataAuth?) {
        val edits = prefs.edit()
        edits.putString(API_TOKEN, "Bearer " + data?.apiToken)
        edits.putString(ID, data?.id.toString())
        edits.putString(NAME, data?.name)
        edits.putString(EMAIL, data?.email)
        edits.putString(NIS, data?.nis)
        edits.putString(KELAS, data?.kelas)
        edits.putString(IMAGE, data?.image)
        edits.apply()
    }

    fun retriveToken(): String? {
        return prefs.getString(API_TOKEN,"")
    }

    fun retriveID(): String? {
        return prefs.getString(ID,"")
    }

    fun retriveName(): String? {
        return prefs.getString(NAME,"")
    }

    fun retriveEmail(): String? {
        return prefs.getString(EMAIL,"")
    }

    fun retriveKelas(): String? {
        return prefs.getString(KELAS,"")
    }

    fun retriveNIS(): String? {
        return prefs.getString(NIS,"")
    }

    fun retriveImage(): String? {
        return prefs.getString(IMAGE,"")
    }


    fun destoryPref() {
        val edits = prefs.edit()
        edits.clear().apply()
    }

    fun hasCachedAccessToken(): Boolean {
        return prefs.contains(API_TOKEN)
    }

    companion object {
        private const val SHARED_PREF = "Authorization"
        private const val API_TOKEN = "Token"
        private const val ID = "ID"
        private const val NAME = "NAME"
        private const val EMAIL = "EMAIL"
        private const val KELAS = "KELAS"
        private const val NIS = "NIS"
        private const val IMAGE = "IMAGE"

    }


}