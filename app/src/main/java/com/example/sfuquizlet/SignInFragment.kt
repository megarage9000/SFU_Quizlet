package com.example.sfuquizlet

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import kotlin.system.exitProcess

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
                exitProcess(-1)
            }
    }
}

fun putUserInDatabase() {
    val completionListener = getCompletionListener()
    val user = getCurrentUser()

    MainActivity.database.getReference("users").child(user.id)
        .setValue(user, completionListener)
}

fun getCompletionListener() : DatabaseReference.CompletionListener {
    return DatabaseReference.CompletionListener {err, ref ->
        if(err != null) {
            Log.i("DATABASE", listOf(err).toString())
        }
    }
}
fun getCurrentUser() : User {
    val id = MainActivity.auth.currentUser!!.uid
    val name = MainActivity.auth.currentUser!!.displayName
    val decks = mutableListOf<String>("")
    val cards = mutableListOf<String>("")
    val clickedCards = mutableListOf<String>("")
    Log.i("DATABASE", "id $id")
    Log.i("DATABASE", "name $name")
    Log.i("DATABASE", "decks $decks")
    Log.i("DATABASE", "cards $cards")
    Log.i("DATABASE", "clickedcards $clickedCards")


    val user = User(id, name!!, decks, cards, clickedCards)

    return user
}
private val PREFS_FILE_NAME = "login"
private val PREFS_VAR_NAME = "firstSignUp"

// is it an issue that getting the current user doesnt actually check the database?
fun MainActivity.login(context : Context) {
    val currentUser = MainActivity.auth.currentUser
    if(currentUser == null) {
        signUp()
        setFirstSignUp(true, context)
    } else {
        if(isFirstSignUp(context) == true) {
            putUserInDatabase()
            setFirstSignUp(false, context)
        }
        toastMessage("Welcome Back ${currentUser.displayName}")
    }
}

fun setFirstSignUp(status: Boolean, context: Context) {
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    val editor: SharedPreferences.Editor = sharedPref.edit()
    editor.putBoolean(PREFS_VAR_NAME, status)
    editor.apply()
}

fun isFirstSignUp(context: Context) : Boolean {
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(PREFS_VAR_NAME, false)
}

fun MainActivity.toastMessage(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_LONG).show()
}