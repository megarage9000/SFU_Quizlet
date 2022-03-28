package com.example.sfuquizlet.database

import android.util.Log
import com.example.sfuquizlet.Deck
import com.example.sfuquizlet.MainActivity
import com.example.sfuquizlet.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate


interface DashboardInfoListener{
    fun onReceivedCardsPracticed(i: Int)
    fun onReceivedCardsAdded(i: Int)
    fun onReceivedUserFavourites(a: Map<String,List<Deck>>)
    fun onReceivedNewCardsToday(i: Int)
}

//Grab the user's favourite decks
fun getUserFavourites(listener: DashboardInfoListener): Map<String,List<Deck>>{
    //Initialize user
    val user = User("","")

    var userFavouriteDecks = arrayListOf<String>()

    var favouritesList = arrayListOf<Deck>()

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

                                // Need these null checks for fields that are lists because of the casting from String to Lists
                                if(deck["cardIds"] != null) {
                                    val cardIds = deck["cardIds"] as MutableList<String>
                                    newDeck.cardIds = cardIds
                                }
                                if(deck["flairIds"] != null) {
                                    val flairIds = deck["flairIds"] as MutableList<String>
                                    newDeck.flairIds = flairIds
                                }

                                favouritesList.add(newDeck)

                                returnDeckList.put(department,favouritesList)
                            }
                        }
                    }
                    //Notify listener
                    val favouritesList = favouritesList.groupBy { it.department }
                    listener.onReceivedUserFavourites(favouritesList)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
            //Access database to compare cards
            MainActivity.database.reference.child("decks")
                .addListenerForSingleValueEvent(getFavouriteDecksListener)
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    //Grab User favourites
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(favDeckListener)

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

//Get new cards today
fun getNewCardsToday(listener: DashboardInfoListener): Int{
    //Initialize user
    val user = User("", "")
    var totalNum: Int = 0

    val cardsAddedListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val fetchedValue = snapshot.value as Map<String, Map<String,String>>
            var numCards: Int = 0
            for(cards in fetchedValue.values){
                val time = cards["timestamp"]
                if(time != null) {
                    //Remove the timestamp, we only care about YYYY/MM/DD
                    val removeHours = time.substringBefore(" ").split("-")
                    val formattedTime = removeHours.map { it.toInt() }
                    val convertedTime = LocalDate.of(formattedTime.get(0),formattedTime.get(1), formattedTime.get(2))

                    //If card was added today, update new cards today
                    if(convertedTime.isEqual(LocalDate.now())) numCards++
                    Log.d("numberBool", convertedTime.isEqual(LocalDate.now()).toString())
                    Log.d("numberConverted", convertedTime.toString())
                    Log.d("numberTimenow", LocalDate.now().toString())
                }

            }
            //Let listener know
            listener.onReceivedNewCardsToday(numCards)
            Log.d("dashboardTime", numCards.toString())
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    //Grab User
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("cards")
        .addListenerForSingleValueEvent(cardsAddedListener)

    return totalNum
}

interface UserFavouritesListener{
    fun onReceivedFavouritesString(incomingArr: ArrayList<String>)
}

fun getUserFavouritesString(listener: UserFavouritesListener): ArrayList<String>{
    //Initialize user
    val user = User("","")

    var userFavouriteDecks = arrayListOf<String>()


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
            listener.onReceivedFavouritesString(userFavouriteDecks)
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    //Grab User favourites
    val currUser = MainActivity.auth.currentUser
    MainActivity.database.reference.child("users").child(currUser!!.uid)
        .addListenerForSingleValueEvent(favDeckListener)

    //Return arraylist of favourite decks
    return userFavouriteDecks
}
