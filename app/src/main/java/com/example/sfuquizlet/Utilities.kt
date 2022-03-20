package com.example.sfuquizlet

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference


fun getCompletionListener() : DatabaseReference.CompletionListener {
    return DatabaseReference.CompletionListener { err, ref ->
        if(err != null) {
            Log.i("DATABASE", listOf(err).toString())
        }
    }
}

fun MainActivity.toastMessage(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_LONG).show()
}