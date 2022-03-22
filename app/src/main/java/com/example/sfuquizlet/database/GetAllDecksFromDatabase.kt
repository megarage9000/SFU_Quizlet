package com.example.sfuquizlet

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

fun getAllDecksFromDatabase(): ArrayList<Deck> {
    // Step 1
    val returnList = arrayListOf<Deck>()

    // Step 3
    val myDecksListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            // The snapshot is the raw data we are getting from the database
            Log.i("TAG", snapshot.value.toString())

            // Here we are casting that raw data to a Map
            val readItems = snapshot.value as Map<String, Map<String, String>>

            for(deck in readItems.values) {
                val id = deck["id"]
                val department = deck["department"]
                val courseNumber = deck["courseNumber"]
                val semester = deck["semester"]
                val year = deck["year"]
                val instructor = deck["instructor"]

                val newDeck = Deck(id!!, department!!, courseNumber!!, semester!!, year!!, instructor!!)

                // Need these null checks for fields that are lists because of the casting from String to Lists
                if(deck["cardIds"] != null) {
                    val cardIds = deck["cardIds"] as MutableList<String>
                    newDeck.cardIds = cardIds
                }
                if(deck["flairIds"] != null) {
                    val flairIds = deck["flairIds"] as MutableList<String>
                    newDeck.flairIds = flairIds
                }

                // Add item to array
                returnList.add(newDeck)
            }
        }
        // Probably just ignore this for now, may add this later if we wanna do error checking
        override fun onCancelled(error: DatabaseError) {

        }
    }

    // Step 2
    MainActivity.database.reference.child("decks")
        .addListenerForSingleValueEvent(myDecksListener)

    // Step 4
    return returnList
}

fun getDecksFromDatabase(listOfDeckIds: MutableList<String>) : ArrayList<Deck>{
    // Step 1
    val returnList = arrayListOf<Deck>()

    // Step 3
    val myDecksListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            // The snapshot is the raw data we are getting from the database
            Log.i("TAG", snapshot.value.toString())

            // Here we are casting that raw data to a Map
            val readItems = snapshot.value as Map<String, String>


                val id = readItems["id"]
                val department = readItems["department"]
                val courseNumber = readItems["courseNumber"]
                val semester = readItems["semester"]
                val year = readItems["year"]
                val instructor = readItems["instructor"]

                val newDeck = Deck(id!!, department!!, courseNumber!!, semester!!, year!!, instructor!!)

                // Need these null checks for fields that are lists because of the casting from String to Lists
                if(readItems["cardIds"] != null) {
                    val cardIds = readItems["cardIds"] as MutableList<String>
                    newDeck.cardIds = cardIds
                }
                if(readItems["flairIds"] != null) {
                    val flairIds = readItems["flairIds"] as MutableList<String>
                    newDeck.flairIds = flairIds


                // Add item to array
                returnList.add(newDeck)
            }
        }
        // Probably just ignore this for now, may add this later if we wanna do error checking
        override fun onCancelled(error: DatabaseError) {

        }
    }

    // Step 2
    for(deckId in listOfDeckIds) {
        MainActivity.database.reference.child("decks").child(deckId)
            .addListenerForSingleValueEvent(myDecksListener)
    }

    // Step 4
    return returnList
}

