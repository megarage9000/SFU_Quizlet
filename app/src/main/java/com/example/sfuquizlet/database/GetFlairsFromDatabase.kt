package com.example.sfuquizlet.database

import android.util.Log
import com.example.sfuquizlet.Flair
import com.example.sfuquizlet.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

fun getFlairsFromDatabase(listOfFlairIds: MutableList<String>): MutableList<Flair> {
    // Step 1
    if(listOfFlairIds.isEmpty()) {
        return mutableListOf()
    }

    // Step 2
    val returnList = mutableListOf<Flair>()

    // Step 4
    val myFlairListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.i("TAG", snapshot.value.toString())

            val readValue = snapshot.value as Map<String, String> // <String, Any>
            val title = readValue["title"]
            val id = readValue["id"]

            val flair = Flair(id!!, title!!)

            Log.i("TAG", flair.toString())
            returnList.add(flair)

        }
        override fun onCancelled(error: DatabaseError) {

        }
    }

    // Step 3
    for(flairId in listOfFlairIds) {
        MainActivity.database.reference.child("flairs").child(flairId)
            .addListenerForSingleValueEvent(myFlairListener)
    }

    // Step 5
    return returnList
}