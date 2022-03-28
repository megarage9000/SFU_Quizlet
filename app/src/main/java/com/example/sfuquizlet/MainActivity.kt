package com.example.sfuquizlet

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

enum class NavBarOptions {
    HOME, COURSES, PROFILE
}

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var auth: FirebaseAuth

        // Placeholder data until we setup sign up feature
        const val EMAIL = "dashboardman7@ayo.code"
        const val NAME = "Sebastian Vettel"
        const val PASS = "StrongPass1234@"

        // access to root node
        val database = Firebase.database
    }

    lateinit var  layoutInflator: LayoutInflater
    lateinit var  frameLayout: FrameLayout
    lateinit var dashBoardView: View

    private lateinit var navBarHomeButton: ImageButton
    private lateinit var navBarCoursesButton: ImageButton
    private lateinit var navBarProfileButton: ImageButton

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

        initializeNavBarBtns()
    }

    private fun initializeNavBarBtns() {
        navBarHomeButton = findViewById<ImageButton>(R.id.navBarHomeID)
        navBarCoursesButton = findViewById<ImageButton>(R.id.navBarCoursesID)
        navBarProfileButton = findViewById<ImageButton>(R.id.navBarProfileID)
        setNavBarListeners()
    }

    private fun setNavBarListeners() {
        // Home Button
        navBarHomeButton.setOnClickListener {
            setNavBarActiveBtn(NavBarOptions.HOME)
            frameLayout.removeAllViews()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayoutID, DashBoardFragment())
                .commit()
        }

        // Courses Button
        navBarCoursesButton.setOnClickListener {
            setNavBarActiveBtn(NavBarOptions.COURSES)
            frameLayout.removeAllViews()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayoutID, CoursesListFragment())
                .commit()
        }

        // Profile Button
        navBarProfileButton.setOnClickListener {
            setNavBarActiveBtn(NavBarOptions.PROFILE)
            frameLayout.removeAllViews()
            frameLayout.addView(layoutInflator.inflate(R.layout.fragment_edit_profile, null))
        }
    }

    private fun setNavBarActiveBtn(active: NavBarOptions) {
        // Reset all nav bar button styles
        resetNavBarBtnStyles(navBarHomeButton)
        resetNavBarBtnStyles(navBarCoursesButton)
        resetNavBarBtnStyles(navBarProfileButton)

        // Set active styles for the selected button
        when(active) {
            NavBarOptions.HOME -> setNavBarActiveBtnStyles(navBarHomeButton)
            NavBarOptions.COURSES -> setNavBarActiveBtnStyles(navBarCoursesButton)
            NavBarOptions.PROFILE -> setNavBarActiveBtnStyles(navBarProfileButton)
        }
    }

    private fun resetNavBarBtnStyles(button: ImageButton) {
        button.setBackgroundResource(0)
        ImageViewCompat.setImageTintList(button, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_grey)));
    }

    private fun setNavBarActiveBtnStyles(button: ImageButton) {
        button.setBackgroundResource(R.drawable.button_bg)
        ImageViewCompat.setImageTintList(button, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
    }
}
