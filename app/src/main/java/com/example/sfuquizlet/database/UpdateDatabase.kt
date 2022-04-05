package com.example.sfuquizlet

import android.util.Log
import com.example.sfuquizlet.database.getUserFromDatabase
import com.google.firebase.auth.UserProfileChangeRequest

// Functions to insert objects into database. Works for updating objects as well, as it will just overwrite the deck/card/flair with the same id

/*But this is an inneficient way to update objects, you could make your own
functions that have an extra child after the id to set the value of just the
updated field similar to the user update functions at the bottom this file*/

fun insertDeck(deck : Deck) {
    MainActivity.database.getReference("decks").child(deck.id)
        .setValue(deck, getCompletionListener())
}

fun insertCard(card: Card, deckId: String, cardIds: MutableList<String>) {
    cardIds.add(card.id)
    // Prevent duplicates
    val cardSet = cardIds.distinct().toList()
    // Add the card to the cards
    MainActivity.database.getReference("cards").child(card.id)
        .setValue(card, getCompletionListener())
    // Update the associated deck
    MainActivity.database.getReference("decks")
        .child(deckId)
        .child("cardIds")
        .setValue(cardSet, getCompletionListener())

    addCardAdded(card.id)
    Log.d("asdf", "added" + card.id)
}

fun deleteCard(deckId: String, cardId: String, cardIds: MutableList<String>) {
    // Remove card from cards
    MainActivity.database.getReference("cards").child(cardId).removeValue()

    // Prevent duplicates
    val cardSet = cardIds.distinct().toList()

    // Remove card from the associated deck
    MainActivity.database.getReference("decks")
        .child(deckId)
        .child("cardIds")
        .setValue(cardSet, getCompletionListener())
}

fun insertFlair(flair: Flair) {
    MainActivity.database.getReference("flairs").child(flair.id)
        .setValue(flair, getCompletionListener())
}

// TODO("Maybe refactor getting the user in each function, instead have it passed in as a parameter to reduce repetitive calls")
fun updateUserViewedCards(cardViewedId: String) {
    val user = getUserFromDatabase()
    user.cardsViewedIds.add(cardViewedId)
    val distinctList = user.cardsViewedIds.distinct().toList()

    MainActivity.database.getReference("users").child(user.id).child("cardsViewedIds")
        .setValue(distinctList, getCompletionListener())
}

fun updateUsername(name: String) {
    val user = MainActivity.auth.currentUser

    MainActivity.database.getReference("users").child(user!!.uid).child("username")
        .setValue(name, getCompletionListener())

    val profileUpdate =  UserProfileChangeRequest.Builder()
        .setDisplayName(name)
        .build()

    MainActivity.auth.currentUser!!.updateProfile(profileUpdate)
}

fun updateUserCreatedCards(cardId: String) {
    val user = getUserFromDatabase()
    user.cardIds.add(cardId)

    MainActivity.database.getReference("users").child(user.id).child("cardIds")
        .setValue(user.cardIds, getCompletionListener())
}

fun addFavouriteDecks(arr: ArrayList<String>) {
    val user = MainActivity.auth.currentUser

    MainActivity.database.getReference("users").child(user!!.uid).child("deckIds")
        .setValue(arr, getCompletionListener())
}

fun removeFavouriteDecks(deckId: String) {
    val user = getUserFromDatabase()
    user.deckIds.remove(deckId)

    MainActivity.database.getReference("users").child(user.id).child("deckIds")
        .setValue(user.cardIds, getCompletionListener())
}

fun addCardViewed(cardId: String){
    val user = MainActivity.auth.currentUser
    var userViewedCardIds = arrayListOf<String>()

    MainActivity.database.getReference("users").child(user!!.uid).child("cardsViewedIds").get().addOnSuccessListener {
        if(it.exists()){
            userViewedCardIds = it.value as ArrayList<String>

            if(!userViewedCardIds.contains(cardId)) {
                userViewedCardIds.add(cardId)
            }

            MainActivity.database.getReference("users").child(user!!.uid).child("cardsViewedIds")
                .setValue(userViewedCardIds)
        }
    }
}

fun addCardAdded(cardId: String){
    val user = MainActivity.auth.currentUser
    var userCardAddedIds = arrayListOf<String>()

    MainActivity.database.getReference("users").child(user!!.uid).child("cardIds").get().addOnSuccessListener {
        if(it.exists()){
            userCardAddedIds = it.value as ArrayList<String>

            if(!userCardAddedIds.contains(cardId)) {
                userCardAddedIds.add(cardId)
            }
            Log.d("asdf", "added" + userCardAddedIds)

            MainActivity.database.getReference("users").child(user!!.uid).child("cardIds")
                .setValue(userCardAddedIds)
        }
    }
}


