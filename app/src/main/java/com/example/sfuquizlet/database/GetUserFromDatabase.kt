package com.example.sfuquizlet.database

import android.os.Message
import android.util.Log
import com.example.sfuquizlet.MainActivity
import com.example.sfuquizlet.User
import com.example.sfuquizlet.toastMessage
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

//testing if it works
public fun MainActivity.getUserFavourites(){

    MainActivity.database.reference.child("users")
        .child("4EI5uO5Z8RUEEHGCJtKrtbTWwoq1").child("deckIds")
        .addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedValue = snapshot.value.toString()
                Log.d("dashboard", fetchedValue)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


}

fun getUserFavourites(){
    val user = User("","")

    val favDeckListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value.toString().split(",")
            val val1 = fetchedValue.get(1)
            Log.d("dashboard alternative", val1)
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid).child("deckIds")
        .addListenerForSingleValueEvent(favDeckListener)
}

