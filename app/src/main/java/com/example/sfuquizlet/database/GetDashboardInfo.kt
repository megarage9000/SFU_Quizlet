package com.example.sfuquizlet.database

import android.util.Log
import com.example.sfuquizlet.MainActivity
import com.example.sfuquizlet.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

fun getUserFavourites(): ArrayList<String>{
    //Initialize user
    val user = User("","")

    var userFavouriteDecks = arrayListOf<String>()

    val favDeckListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value as Map<String, Map<String,String>>

            if(fetchedValue["deckIds"] != null){
                val deckIds = fetchedValue["deckIds"]
                if(deckIds != null){
                    userFavouriteDecks = ArrayList(deckIds.values)
                    for(i in userFavouriteDecks){
                        Log.d("dash2", i)
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    //Grab User
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(favDeckListener)

    //Return arraylist of favourite decks
    return userFavouriteDecks
}

interface CardsPracticedListener{
    fun onReceivedCardsPracticed(i: Int)
    fun onReceivedCardsAdded(i: Int)
}

//Get number of cards practiced
fun getUserCardsPracticed(listener: CardsPracticedListener): Int{
    //Initialize user
    val user = User("", "")
    var totalNum: Int = 0

    val cardsPracticedListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value as Map<String, Map<String,String>>

            if(fetchedValue["cardsViewedIds"] != null){
                val cardsViewed = fetchedValue["cardsViewedIds"] as MutableList<String>
                totalNum = cardsViewed.size
                listener.onReceivedCardsPracticed(totalNum)
            }
            Log.d("dash", "total num "+totalNum)
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    //Grab User
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(cardsPracticedListener)

    return totalNum
}

//Same as getting practiced but for cards added
fun getCardsAdded(listener: CardsPracticedListener): Int{
    //Initialize user
    val user = User("", "")
    var totalNum: Int = 0

    val cardsPracticedListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value as Map<String, Map<String,String>>

            if(fetchedValue["cardIds"] != null){
                val cardsViewed = fetchedValue["cardIds"] as MutableList<String>
                totalNum = cardsViewed.size
                listener.onReceivedCardsAdded(totalNum)
            }
            Log.d("dash", "card ids total num "+totalNum)
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    //Grab User
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(cardsPracticedListener)

    return totalNum
}