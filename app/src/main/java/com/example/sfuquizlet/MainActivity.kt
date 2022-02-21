package com.example.sfuquizlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutInflator = LayoutInflater.from(this)
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutID)
        val dashBoardView = layoutInflator.inflate(R.layout.dashboard, null)
        frameLayout.addView(dashBoardView)

        val navBarHomeButton = findViewById<ImageView>(R.id.navBarHomeID)
        navBarHomeButton.setOnClickListener {
            frameLayout.removeAllViews()
            frameLayout.addView(layoutInflator.inflate(R.layout.dashboard, null))
        }

        val navBarCoursesButton = findViewById<ImageView>(R.id.navBarCoursesID)
        navBarCoursesButton.setOnClickListener {
            frameLayout.removeAllViews()
            val courseListView = layoutInflator.inflate(R.layout.course_list, null)
            val testView = courseListView.findViewById<View>(R.id.OpenStudyDeck)
            testView.setOnClickListener {
                frameLayout.removeAllViews()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayoutID, StudyDeckFragment())
                    .commit()
            }
            frameLayout.addView(courseListView)
        }

        val navBarProfileButton = findViewById<ImageView>(R.id.navBarProfileID)
        navBarProfileButton.setOnClickListener {
            frameLayout.removeAllViews()
            frameLayout.addView(layoutInflator.inflate(R.layout.fragment_edit_profile, null))
        }
    }
}