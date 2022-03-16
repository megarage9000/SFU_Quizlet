package com.example.sfuquizlet

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.UserProfileChangeRequest

fun MainActivity.signUp() {
    MainActivity.auth.createUserWithEmailAndPassword(MainActivity.EMAIL, MainActivity.PASS)
        .addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                val profileUpdate =  UserProfileChangeRequest.Builder()
                    .setDisplayName(MainActivity.NAME)
                    .build()

                MainActivity.auth.currentUser!!.updateProfile(profileUpdate)

                toastMessage("Successfully Registered")
            } else {
                toastMessage("Registration Failed")
                Log.i("TAG", task.exception.toString())
            }
    }
}

// is it an issue that getting the current user doesnt actually check the database?
fun MainActivity.login() {
    val currentUser = MainActivity.auth.currentUser
    if(currentUser == null) {
        signUp()
    } else {
        val username = MainActivity.auth.currentUser!!.displayName
        toastMessage("Welcome Back $username")
    }
}

fun MainActivity.toastMessage(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_LONG).show()
}