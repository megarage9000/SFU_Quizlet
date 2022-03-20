package com.example.sfuquizlet.database

import android.util.Log
import com.example.sfuquizlet.MainActivity
import com.example.sfuquizlet.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

fun getUserFromDatabase(): User {
    // Step 1
    var user = User("","")

    // Step 3
    val myCardListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.i("TAG", snapshot.value.toString())

            val readValue = snapshot.value as Map<String, String> // <String, Any>
            val id = readValue["id"]
            val username = readValue["username"]

            user = User(
                id = id!!,
                username = username!!
            )
            if(readValue["cardIds"] != null) {
                val cardIds = readValue["cardIds"] as MutableList<String>
                user.cardIds = cardIds
            }
            if(readValue["cardViewedIds"] != null) {
                val cardViewedIds = readValue["cardViewedIds"] as MutableList<String>
                user.cardsViewedIds = cardViewedIds
            }
            if(readValue["deckIds"] != null) {
                val deckIds = readValue["deckIds"] as MutableList<String>
                user.deckIds = deckIds
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    // Step 2
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(myCardListener)

    // Step 4
    return user
}