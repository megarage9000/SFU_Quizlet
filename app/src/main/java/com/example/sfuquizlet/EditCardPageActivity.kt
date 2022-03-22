package com.example.sfuquizlet

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sfuquizlet.database.getCardsFromDatabase
import com.example.sfuquizlet.database.getFlairsFromDatabase
import com.example.sfuquizlet.databinding.ActivityEditCardPageBinding
import java.util.*

lateinit var binding : ActivityEditCardPageBinding

class EditCardPageActivity : AppCompatActivity() {

    lateinit var QuestionContent: FillInCards
    lateinit var AnswerContent: FillInCards
    lateinit var FlairView: FlairEditor
    lateinit var card: Card
    lateinit var deck: Deck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCardPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val deckId = intent.getStringExtra("deckId")
        val cardId: String? = intent.getStringExtra("cardId")
        val titleName = intent.getStringExtra("titleName")
        val submitButtonName = intent.getStringExtra("submitButtonName")

        binding.Title.text = titleName
        binding.SubmitButton.text = submitButtonName
        binding.SubmitButton.setOnClickListener {
            // Add an update here
            updateDatabase()
            finish()
        }

        lateinit var answer: String
        lateinit var question: String
        lateinit var flairs: MutableList<Flair>

        if(deckId != null){
            val decks = getDecksFromDatabase(mutableListOf(deckId))
            if(decks.size > 0) {
                deck = decks[0]
            }
        }

        // Edit card
        if(cardId != null) {
            val cards = getCardsFromDatabase(mutableListOf(cardId))
            if(cards.isNotEmpty()) {
                card = cards[0]
            }
        }
        // Add card
        else{
            card = Card(
                "",
                "",
                "",
                mutableListOf()
            )
        }
        answer = card.answer
        question = card.question
        flairs = getFlairsFromDatabase(card.flairIds)

        QuestionContent = FillInCards("Question", "Enter Question", question, this, binding.QuestionView)
        AnswerContent = FillInCards("Answer", "Enter Answer", answer, this, binding.AnswerView)
        FlairView = FlairEditor(this, flairs, binding.FlairView)
    }

    companion object {
        fun OpenEditCard(context: Context, cardId: String, deckId: String) {
            val intent = Intent(context, EditCardPageActivity::class.java)
            intent.putExtra("deckId", deckId)
            intent.putExtra("cardId", cardId)
            intent.putExtra("titleName", "Edit Card")
            intent.putExtra("submitButtonName", "Save Card")
            context.startActivity(intent)
        }

        fun OpenAddCard(context: Context, deckId: String) {
            val intent = Intent(context, EditCardPageActivity::class.java)
            intent.putExtra("deckId", deckId)
            intent.putExtra("titleName", "Edit Card")
            intent.putExtra("submitButtonName", "Save Card")
            context.startActivity(intent)
        }
    }

    fun updateDatabase() {
        // If the card doesn't have an id, add it
        if(card.id == ""){
            card.id = UUID.randomUUID().toString()
        }
        card.answer = AnswerContent.getContent()
        card.question = QuestionContent.getContent()
        val flairs = FlairView.getFlairs()
        val flairIds = card.flairIds

        // Checks flairs from the activity,
        // and flairs from the card to see
        // if new flairs are added
        for(flair in flairs) {
            if(flair.id !in flairIds) {
                // Update the flair id list
                // and insert flair to database
                flairIds.add(flair.id)
                insertFlair(flair)
            }
        }
        // Set flair ids to the new set of flair ids
        card.flairIds = flairIds
        insertCard(card)

        // Update the deck as well
        if(deck != null) {
            val deckFlairs = deck.flairIds
            val cardFlairs = card.flairIds
            for(cardFlair in cardFlairs) {
                if(cardFlair !in deckFlairs) {
                    deckFlairs.add(cardFlair)
                }
            }
            deck.flairIds = deckFlairs
            insertDeck(deck)
        }
    }
}



// John: A not so good way with using XMLs on code. Would have been much better to do fragments and linear layouts
// Please don't do this!

// For Input Cards
class FillInCards(private val title: String, private val textHint: String, var textContent: String, val context: Context, val parent: FrameLayout) {

    var view: View
    val charLimit: Int = 250

    init {
        // Create the view and set the view
        // to the given frame layout
        val inflater = LayoutInflater.from(context)
        val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            600
        )
        view = inflater.inflate(R.layout.fill_in_card, null, false)
        parent.addView(view, layoutParams)
        setContent()
    }

    private fun setContent() {
        // Set the title
        val titleText = view.findViewById<TextView>(R.id.CardTitle)
        titleText.text = title

        // Set the body content
        val body = view.findViewById<EditText>(R.id.CardInput)
        body.hint = textHint
        body.setText(textContent)

        // Adding body content filter and listener
        val charCounter = view.findViewById<TextView>(R.id.CharacterCount)
        body.filters = arrayOf(InputFilter.LengthFilter(charLimit))
        charCounter.text = "${body.text.length}/$charLimit Character Limit"
        body.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    return
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    return
                }

                override fun afterTextChanged(p0: Editable?) {
                    var numChars = body.text.length;
                    charCounter.text = "$numChars/$charLimit Character Limit"
                }
            }
        )
    }

    fun getContent() : String {
        return view.findViewById<EditText>(R.id.CardInput).toString()
    }
}

// For the Flair editor
class FlairEditor(val context: Context, private val flairs: MutableList<Flair>, val parent: FrameLayout) {
    var view: View
    init {
        // Create the view and set the view
        // to the given frame layout
        val inflater = LayoutInflater.from(context)
        val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view = inflater.inflate(R.layout.flair_input, null, false)
        parent.addView(view, layoutParams)
        setContent()
        setInput()
    }

    private fun setContent() {
        // Initializes the flair view recycler, with the given flairs
        val flairList = view.findViewById<RecyclerView>(R.id.FlairList)
        flairList.layoutManager = object : StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) {
            // Disable scrolling on both directions
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        flairList.adapter = FlairViewRecycler(flairs, context, LayoutInflater.from(context))
    }

    private fun setInput() {
        // Initializing the input method for flairs
        val inputField = view.findViewById<EditText>(R.id.FlairNameInput)
        val submit = view.findViewById<TextView>(R.id.FlairSubmit)
        val flairList = view.findViewById<RecyclerView>(R.id.FlairList)
        submit.setOnClickListener {
            (flairList.adapter as FlairViewRecycler).addItem(
                inputField.text.toString()
            )
        }
    }

    fun getFlairs() : MutableList<Flair> {
        return flairs
    }
}





