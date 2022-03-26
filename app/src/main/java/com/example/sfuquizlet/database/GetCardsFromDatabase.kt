package com.example.sfuquizlet.database

import android.util.Log
import com.example.sfuquizlet.Card
import com.example.sfuquizlet.CardsRecyclerViewAdapter
import com.example.sfuquizlet.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

fun getCardsFromDatabase(listOfCardIds: MutableList<String>, cardsRecyclerViewAdapter: CardsRecyclerViewAdapter) {
    // Step 1
    if(listOfCardIds.isEmpty()) {
        return
    }

    // Step 2
    val myCardListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.i("TAG", snapshot.value.toString())

            val readValue = snapshot.value as Map<String, String> // <String, Any>
            val id = readValue["id"]
            val question = readValue["question"]
            val answer = readValue["answer"]
            val authorId = readValue["authorId"]
            val timestamp = readValue["timestamp"]

            val card = Card(
                id = id!!,
                question = question!!,
                answer = answer!!,
                authorId = authorId!!,
                timestamp = timestamp!!
            )
            if(readValue["flairIds"] != null) {
                val flairIds = readValue["flairIds"] as MutableList<String>
                card.flairIds = flairIds
            }
            cardsRecyclerViewAdapter.addCard(card)
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    // Step 3
    for(cardId in listOfCardIds) {
        MainActivity.database.reference.child("cards").child(cardId)
            .addListenerForSingleValueEvent(myCardListener)
    }
}