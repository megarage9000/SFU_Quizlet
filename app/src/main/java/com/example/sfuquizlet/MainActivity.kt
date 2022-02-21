package com.example.sfuquizlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navBarHomeButton = findViewById<ImageView>(R.id.navBarHomeID)
        navBarHomeButton.setOnClickListener {
            // TODO
        }

        val navBarCoursesButton = findViewById<ImageView>(R.id.navBarCoursesID)
        navBarCoursesButton.setOnClickListener {
            // TODO
        }

        val navBarProfileButton = findViewById<ImageView>(R.id.navBarProfileID)
        navBarProfileButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerViewID, EditProfileFragment())
            transaction.commit()
        }

    }
}