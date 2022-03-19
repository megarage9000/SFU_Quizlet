package com.example.sfuquizlet

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

fun getAllDecks() {
    MainActivity.database.reference.child("decks")
        .addListenerForSingleValueEvent(
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // The snapshot is the raw data we are getting from the database
                Log.i("TAG", snapshot.value.toString())

                // Here we are casting that raw data to a Map, probably dont need to change this for cards, maybe change for flairs
                val readItems = snapshot.value as Map<String, Map<String, String>>

                // Change this to Card/Flair
                val itemsArr = arrayListOf<Deck>()

                for(each in readItems.values) {
                    val id = each["id"]
                    val department = each["department"]
                    val courseNumber = each["courseNumber"]
                    val semester = each["semester"]
                    val year = each["year"]
                    val instructor = each["instructor"]

                    val newItem = Deck(id!!, department!!, courseNumber!!, semester!!, year!!, instructor!!)

                    // Need these null checks for fields that are lists because of the casting from String to Lists
                    if(each["cardIds"] != null) {
                        val cardIds = each["cardIds"] as MutableList<String>
                        newItem.cardIds = cardIds
                    }
                    if(each["flairIds"] != null) {
                        val flairIds = each["flairIds"] as MutableList<String>
                        newItem.flairIds = flairIds
                    }

                    // Add item to array
                    itemsArr.add(newItem)
                }
//                Show output array
                Log.i("TAG", itemsArr.toString())
            }
//          Probably just ignore this for now, may add this later if we wanna do error checking
            override fun onCancelled(error: DatabaseError) {

            }
        }
    )
}

fun getCardsFromList(listOfCardIds: MutableList<String>) : List<Card> {

    val returnList = mutableListOf<Card>()

    val myListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.i("TAG", snapshot.value.toString())

            val readValue = snapshot.value as Map<String, String> // <String, Any>
            val id = readValue["id"]
            val question = readValue["question"]
            val answer = readValue["answer"]
            val authorId = readValue["authorId"]
            val timestamp = readValue["timestamp"]

            val card = Card(id = id!!, question = question!!, answer = answer!!, authorId = authorId!!, timestamp = timestamp!!)
            if(readValue["flairIds"] != null) {
                val flairIds = readValue["flairIds"] as MutableList<String>
                card.flairIds = flairIds
            }
            returnList.add(card)
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    for(cardId in listOfCardIds) {
        MainActivity.database.reference.child("cards").child(cardId)
            .addListenerForSingleValueEvent(myListener)
    }

    return returnList
}

fun getFlairsFromList(listOfFlairIds: MutableList<String>): MutableList<Flair> {

    val returnList = mutableListOf<Flair>()

    val myListener = object : ValueEventListener {
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

    for(flairId in listOfFlairIds) {
        MainActivity.database.reference.child("flairs").child(flairId)
            .addListenerForSingleValueEvent(myListener)
    }

    return returnList
}