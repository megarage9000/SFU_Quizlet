package com.example.sfuquizlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.sfuquizlet.database.getUserFromDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var auth: FirebaseAuth

        // Placeholder data until we setup sign up feature
        const val EMAIL = "dashboardman@ayo.code"
        const val NAME = "Sebastian Vettel"
        const val PASS = "StrongPass1234@"

        // access to root node
        val database = Firebase.database
    }

    lateinit var  layoutInflator: LayoutInflater
    lateinit var  frameLayout: FrameLayout
    lateinit var dashBoardView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        login(this) // SignInFragment.kt

        layoutInflator = LayoutInflater.from(this)
        frameLayout = findViewById(R.id.frameLayoutID)
//        dashBoardView = layoutInflator.inflate(R.layout.course_list, null)
//
//        frameLayout.addView(dashBoardView)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutID, DashBoardFragment())
            .commit()

        initializeNavBarHomeBtn()
        initializeNavBarCoursesBtn()
        initializeNavBarProfileBtn()

    }

    private fun initializeNavBarHomeBtn() {
        val navBarHomeButton = findViewById<ImageView>(R.id.navBarHomeID)
        navBarHomeButton.setOnClickListener {
            frameLayout.removeAllViews()
//            frameLayout.addView(layoutInflator.inflate(R.layout.dashboard, null))
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayoutID, DashBoardFragment())
                .commit()
        }
    }

    private fun initializeNavBarCoursesBtn() {
        val navBarCoursesButton = findViewById<ImageView>(R.id.navBarCoursesID)
        navBarCoursesButton.setOnClickListener {
            frameLayout.removeAllViews()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayoutID, CoursesListFragment())
                .commit()
//            val courseListView = layoutInflator.inflate(R.layout.course_list, null)
//            val testView = courseListView.findViewById<View>(R.id.OpenStudyDeck)
//            testView.setOnClickListener {
//                frameLayout.removeAllViews()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.frameLayoutID, StudyDeckFragment())
//                    .commit()
//            }
//            frameLayout.addView(courseListView)
        }
    }

    private fun initializeNavBarProfileBtn() {
        val navBarProfileButton = findViewById<ImageView>(R.id.navBarProfileID)
        navBarProfileButton.setOnClickListener {
            frameLayout.removeAllViews()
            frameLayout.addView(layoutInflator.inflate(R.layout.fragment_edit_profile, null))
        }
    }
}