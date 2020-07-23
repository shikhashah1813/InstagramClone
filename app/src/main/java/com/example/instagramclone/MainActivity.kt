package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                textView.setText("Home")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search -> {
                textView.setText("Search")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_add -> {
                textView.setText("Add Post")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_heart -> {
                textView.setText("Notifications")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                textView.setText("My Profile")
                return@OnNavigationItemSelectedListener true
            }
        }

        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        textView = findViewById(R.id.message)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}