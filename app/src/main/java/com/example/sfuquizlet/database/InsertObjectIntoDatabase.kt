package com.example.sfuquizlet
// Functions to insert objects into database. Works for editing objects as well, as it will just overwrite the deck/card/flair with the same id


fun insertDeck(deck : Deck) {
    MainActivity.database.getReference("decks").child(deck.id)
        .setValue(deck, getCompletionListener())
}

fun insertCard(card: Card) {
    MainActivity.database.getReference("cards").child(card.id)
        .setValue(card, getCompletionListener())
}

fun insertFlair(flair: Flair) {
    MainActivity.database.getReference("flairs").child(flair.id)
        .setValue(flair, getCompletionListener())
}