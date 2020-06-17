package com.darmajayamlearnings

import android.os.Bundle
import com.bumptech.glide.Glide
import com.darmajaya.globalsurya.API.ApiClient.BASE_URL
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nis.setText(retrieveNIS())
        name.setText(retrieveName())
        kelas.setText(retrieveKelas())
        email.setText(retrieveEmail())

        Glide.with(this)
            .load(BASE_URL+"storage/images/original/"+retrieveImage())
            .fitCenter()
            .into(image)
    }
}