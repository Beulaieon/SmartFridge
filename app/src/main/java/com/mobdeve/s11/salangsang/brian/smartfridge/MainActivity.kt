package com.mobdeve.s11.salangsang.brian.smartfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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