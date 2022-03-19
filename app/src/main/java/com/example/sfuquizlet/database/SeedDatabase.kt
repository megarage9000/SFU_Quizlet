package com.example.sfuquizlet

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

// Dont call any of these functions. Will mess up the ids in the database. The database is already seeded

fun seedDatabase() {
//    seedDecks()
//    seedCards()
//    seedFlairs()
}

fun seedDecks() {
    val deck1 = Deck(UUID.randomUUID().toString(),"CMPT","135","Spring","2022","Brian Fraser",
        mutableListOf("00006e51-058b-4a59-b5e7-bbbdc9ab00a4", "0c7c5856-7b9f-43e8-b109-97a8c076befd","2b74b63a-ed5c-4c39-b997-93f790d623c6","9b62edb6-bdf3-4c93-911f-b53567a91680"),
        mutableListOf("2d8ab677-99e4-4741-a195-473827cba0de", "3882af38-62fe-4dfd-975f-6d7a93ea821e", "ee7b66c9-e167-4d6a-a48a-3b365aa16680"))
    val deck2 = Deck(UUID.randomUUID().toString(),"CMPT","363","Spring","2022","Paul Hibbits")
    val deck3 = Deck(UUID.randomUUID().toString(),"CMPT","433","Spring","2022","Brian Fraser")
    val deck4 = Deck(UUID.randomUUID().toString(),"CMPT","473","Spring","2022","Steven Ko")

    val deck5 = Deck(UUID.randomUUID().toString(),"IAT","359","Spring","2022","Amir J",
        mutableListOf("312f0f46-721a-44ac-bf87-1ecef6c5d829", "57da721b-d03e-49b0-a1ef-d1610a0796f0", "7fa44cbe-0d79-446a-acd6-9d4769a3120f", "daee8a27-66f4-43c9-86a6-10801f935534"),
        mutableListOf("7cd6d013-510e-40d4-bb7d-d271d2d68a25", "38642d44-cee1-46b3-85b5-3920f10bd6a5"))
    val deck6 = Deck(UUID.randomUUID().toString(),"IAT","267","Spring","2022","Yingchen Yang & Amal Vincent")
    val deck7 = Deck(UUID.randomUUID().toString(),"CHEM","110","Spring","2022","Rebecca Goyan")
    val deck8 = Deck(UUID.randomUUID().toString(),"COGS","100","Spring","2022","Jeremy Turner")

    val deck9 = Deck(UUID.randomUUID().toString(),"BUS","200","Fall","2021","John Doe")
    val deck10 = Deck(UUID.randomUUID().toString(),"CMPT","105W","Fall","2021","Steven Pearce")

    val decks = listOf(deck1, deck2, deck3, deck4, deck5, deck6, deck7, deck8, deck9, deck10)

    for (deck in decks) {
        MainActivity.database.getReference("decks").child(deck.id)
            .setValue(deck, getCompletionListener())
    }
}

fun seedCards() {
    val user = MainActivity.auth.currentUser

    // Cards for IAT 359 deck
    val card1 = Card(question = "what is Kotlin?", answer = "A programming language")
    val card2 = Card(question = "what is Android Studios used for?", answer = "develop mobile applications for Android")
    val card3 = Card(question = "whats the difference between recycler views and linear layouts?", answer = "recycler views are used for dynamic content")
    val card4 = Card(question = "whats an API vs SDK?", answer = "")

    // Cards for CMPT 135 deck
    val card5 = Card(question = "what is a loop vs an if statement?", answer = "a loop checks a condition and does an action n times, an if statement checks a condition and does an action just once")
    val card6 = Card(question = "whats an array?", answer = "a collection of variables")
    val card7 = Card(question = "whats the difference between * and &?", answer = "basically opposites. * gets the value stored in a pointer, while & gets the physical address of a pointer")
    val card8 = Card(question = "why is 5var an invalid variable name?", answer = "because it starts with a number")

    val cards = listOf(card1, card2, card3, card4, card5, card6, card7, card8)

    for(card in cards) {
        card.id = UUID.randomUUID().toString()
        card.timestamp = getTimestamp()!!
        card.authorId = user!!.uid

        MainActivity.database.getReference("cards").child(card.id)
            .setValue(card, getCompletionListener())
    }

}

fun getTimestamp(): String? {
    val timestamp = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    return timestamp
}

fun seedFlairs() {
    val flair1 = Flair(UUID.randomUUID().toString(), "Introduction")
    val flair2 = Flair(UUID.randomUUID().toString(), "Intermediate UI")

    val flair3 = Flair(UUID.randomUUID().toString(), "Midterm 1")
    val flair4 = Flair(UUID.randomUUID().toString(), "Midterm 2")
    val flair5 = Flair(UUID.randomUUID().toString(), "Final")

    val flairs = listOf(flair1, flair2, flair3, flair4, flair5)

    for(flair in flairs) {
        MainActivity.database.getReference("flairs").child(flair.id)
            .setValue(flair, getCompletionListener())
    }
}