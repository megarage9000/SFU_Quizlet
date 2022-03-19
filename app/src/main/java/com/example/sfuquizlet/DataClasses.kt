package com.example.sfuquizlet

data class User(val id: String,
                var username: String,
                var deckIds: MutableList<String> = mutableListOf(),
                var cardIds: MutableList<String> = mutableListOf(),
                var cardsViewedIds: MutableList<String> = mutableListOf()
)

data class Deck(val id: String,
                val department: String,
                val courseNumber: String,
                var semester: String,
                var year: String,
                val instructor: String,
                var cardIds: MutableList<String> = mutableListOf(),
                var flairIds: MutableList<String> = mutableListOf()
)

data class Card(
    var id: String = "",
    var question: String,
    var answer: String,
    var flairIds: MutableList<String> = mutableListOf(),
    var timestamp: String = "",
    var authorId: String = ""
)

data class Flair(val id: String,
                 val title: String
)

