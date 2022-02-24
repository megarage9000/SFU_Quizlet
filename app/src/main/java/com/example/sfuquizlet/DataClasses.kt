package com.example.sfuquizlet

data class User(val id: String,
                var username: String,
                var deckIds: MutableList<String>,
                var cardIds: MutableList<String>,
                var cardsViewedIds: MutableList<String>)

data class Deck(val id: String,
                val name: String,
                val instructor: String,
                var cardIds: MutableList<String>,
                var flairIds: MutableList<String>)

data class Card(val id: String,
                val deckId: String,
                var question: String,
                var answer: String,
                var flairIds: MutableSet<String>,
                var timestamp: String)

data class Flair(val id: String,
                 val deckId: String,
                 val title: String,
                 val color: String)

