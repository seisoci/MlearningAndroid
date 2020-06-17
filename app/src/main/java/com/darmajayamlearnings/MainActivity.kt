package com.darmajayamlearnings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setTitle("Selamat Datang, " + retrieveName())

        logout.setOnClickListener {
            tokenExpired()
        }

        profile.setOnClickListener {
            val i = Intent(this, ProfileActivity::class.java)
            this.startActivity(i)
        }

        kelas.setOnClickListener {
            val i = Intent(this, KelasActivity::class.java)
            this.startActivity(i)
        }

        about.setOnClickListener {
            val i = Intent(this, AboutActivity::class.java)
            this.startActivity(i)
        }
    }
}