package com.example.qr_go

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class Usine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usine)

        // get reference to ImageView
        val ticket_click = findViewById(R.id.imgticket) as ImageView
        val back_click = findViewById(R.id.imgbacked) as ImageView
        val duplicata_click = findViewById(R.id.imgdup) as ImageView
        // set on-click listener
        ticket_click.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            val intent = Intent(this, GenerActivity::class.java)
            // start your next activity
            startActivity(intent)

            Toast.makeText(this@Usine, "ticket.", Toast.LENGTH_SHORT).show()
        }

        back_click.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            onBackPressed()
            Toast.makeText(this@Usine, "back.", Toast.LENGTH_SHORT).show()
        }
        duplicata_click.setOnClickListener {

            // your code to perform when the user clicks on the ImageView
            val intent = Intent(this, DuplicataActivity::class.java)
            // start your next activity
            startActivity(intent)
            Toast.makeText(this@Usine, "duplicata.", Toast.LENGTH_SHORT).show()
        }
    }
}


