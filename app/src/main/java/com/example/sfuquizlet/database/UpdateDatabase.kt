package com.example.sfuquizlet

import com.example.sfuquizlet.database.getUserFromDatabase

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

fun updateUserCreatedCards(cardId: String) {
    val user = getUserFromDatabase()
    user.cardIds.add(cardId)

    MainActivity.database.getReference("users").child(user.id).child("cardIds")
        .setValue(user.cardIds, getCompletionListener())
}

fun addFavouriteDecks(deckId: String) {
    val user = getUserFromDatabase()
    user.deckIds.add(deckId)

    MainActivity.database.getReference("users").child(user.id).child("deckIds")
        .setValue(user.cardIds, getCompletionListener())
}

fun removeFavouriteDecks(deckId: String) {
    val user = getUserFromDatabase()
    user.deckIds.remove(deckId)

    MainActivity.database.getReference("users").child(user.id).child("deckIds")
        .setValue(user.cardIds, getCompletionListener())
}