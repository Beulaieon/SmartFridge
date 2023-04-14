package com.mobdeve.s11.salangsang.brian.smartfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.ActivityHomePageBinding
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.ActivityMainBinding

class HomePage : AppCompatActivity() {

    lateinit var binding : ActivityHomePageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeFragment())

        binding.BotNavbar.setOnItemSelectedListener {

            when(it.itemId){

                R.id.homeNav -> replaceFragment(homeFragment())
                R.id.notificationNav -> replaceFragment(notificationFragment())
                R.id.settingsNav -> replaceFragment(settingsFragment())

                else -> {

                }
            }
            true
        }



    }


    // FUNCTION FOR SWITCHING FRAGMENT IN NAVBAR
    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }


}