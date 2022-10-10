package com.example.qr_go

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class DuplicataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duplicata)

        // get reference to ImageView

        val back_click = findViewById(R.id.imgbacked) as ImageView
        // set on-click listener

        back_click.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            onBackPressed()
            Toast.makeText(this@DuplicataActivity, "back.", Toast.LENGTH_SHORT).show()
        }
    }
}


