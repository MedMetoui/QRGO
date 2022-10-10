package com.example.qr_go

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity



class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // get reference to ImageView
        val iv_click_m = findViewById(R.id.imageView4) as ImageView
        val iv_click_me = findViewById(R.id.imageView5) as ImageView
        // set on-click listener
        iv_click_m.setOnClickListener {
            val intent = Intent(this, Usine::class.java)
            // start your next activity
            startActivity(intent)
        }




        iv_click_me.setOnClickListener {


            val intent = Intent(this, Logistique::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}


