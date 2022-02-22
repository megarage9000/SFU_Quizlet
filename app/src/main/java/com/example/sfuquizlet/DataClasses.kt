package com.example.sfuquizlet

data class User(val id: String,
                val username: String,
                val courses: MutableList<String>,
                val cards: MutableList<String>,
                val cardsViewed: MutableSet<String>)

data class Deck(val id: String,
                val name: String,
                val instructor: String,
                val cardIds: MutableList<String>,
                val flairIds: MutableList<String>)

data class Card(val id: String,
                val deckId: String,
                val question: String,
                val answer: String,
                val flairIds: MutableSet<String>,
                val timestamp: String)

data class Flair(val id: String,
                 val deckId: String,
                 val title: String,
                 val color: String)

