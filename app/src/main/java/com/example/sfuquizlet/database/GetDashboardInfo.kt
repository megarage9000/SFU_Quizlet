package com.example.sfuquizlet.database

import android.util.Log
import com.example.sfuquizlet.Deck
import com.example.sfuquizlet.MainActivity
import com.example.sfuquizlet.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

interface DashboardInfoListener{
    fun onReceivedCardsPracticed(i: Int)
    fun onReceivedCardsAdded(i: Int)
    fun onReceivedUserFavourites(a: Map<String,List<Deck>>)
}

//Grab the user's favourite decks
fun getUserFavourites(listener: DashboardInfoListener): Map<String,List<Deck>>{
    //Initialize user
    val user = User("","")

    var userFavouriteDecks = arrayListOf<String>()

    var favouritesList = mutableListOf<Deck>()

    var returnDeckList = mutableMapOf<String,List<Deck>>()

    val favDeckListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value as Map<String, Map<String,String>>

            if(fetchedValue["deckIds"] != null){
                val deckIds = fetchedValue["deckIds"] as ArrayList<String>
                if(deckIds != null){
                    userFavouriteDecks = deckIds
                    for(i in userFavouriteDecks){
                        Log.d("dash2", i)
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }
    //Compare user decks to decks database
    val getFavouriteDecksListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value as Map<String, Map<String, String>>

            //Grab the decks that are favourited
            for (string in userFavouriteDecks){
                for(deck in fetchedValue.values){
                    if(string == deck["id"]){

                        val id = deck["id"]
                        val department = deck["department"]
                        val courseNumber = deck["courseNumber"]
                        val semester = deck["semester"]
                        val year = deck["year"]
                        val instructor = deck["instructor"]

                        val newDeck = Deck(id!!, department!!, courseNumber!!, semester!!, year!!, instructor!!)

                        favouritesList.add(newDeck)

                        returnDeckList.put(department,favouritesList)
                    }
                }
            }
            //Notify listener
            listener.onReceivedUserFavourites(returnDeckList)
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    //Grab User favourites
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(favDeckListener)

    //Grab the favourite decks from decks database
    MainActivity.database.reference.child("decks")
        .addListenerForSingleValueEvent(getFavouriteDecksListener)

    //Return arraylist of favourite decks
    return returnDeckList
}

//Get number of cards practiced
fun getUserCardsPracticed(listener: DashboardInfoListener): Int{
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
fun getCardsAdded(listener: DashboardInfoListener): Int{
    //Initialize user
    val user = User("", "")
    var totalNum: Int = 0

    val cardsAddedListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value as Map<String, Map<String,String>>

            if(fetchedValue["cardIds"] != null){
                val cardsViewed = fetchedValue["cardIds"] as MutableList<String>
                totalNum = cardsViewed.size
                listener.onReceivedCardsAdded(totalNum)
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    //Grab User
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(cardsAddedListener)

    return totalNum
}

