package com.mobdeve.s11.salangsang.brian.smartfridge

//    MOBDEVE - S11
//    MP GROUP 21 - SmartFridge

//      Julia Dalipe
//      Geryco Dionisio
//      Brian Salangsang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getStartedBtn = findViewById<Button>(R.id.getStartedBtn)

        // link to homepage
        getStartedBtn.setOnClickListener{
            Toast.makeText(this, "Home button Clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this,HomePage::class.java)
            startActivity(intent)
        }
    }



}