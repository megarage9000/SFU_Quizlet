package com.example.sfuquizlet

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sfuquizlet.databinding.ActivityEditCardPageBinding

lateinit var binding : ActivityEditCardPageBinding

interface EditCardListener {
    fun onEditCardClose(card: Card)
}

class EditCardPageActivity : AppCompatActivity() {

    lateinit var QuestionContent: FillInCards
    lateinit var AnswerContent: FillInCards
    lateinit var FlairView: FlairEditor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCardPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val titleName = intent.getStringExtra("titleName")
        val submitButtonName = intent.getStringExtra("submitButtonName")

        binding.Title.text = titleName
        binding.SubmitButton.text = submitButtonName
        binding.SubmitButton.setOnClickListener {
            card.answer = AnswerContent.getContent()
            card.question = QuestionContent.getContent()
            listener.onEditCardClose(card)
            finish()
        }

        val answer: String = card.answer
        val question: String = card.question

        QuestionContent = FillInCards("Question", "Enter Question", question, this, binding.QuestionView)
        AnswerContent = FillInCards("Answer", "Enter Answer", answer, this, binding.AnswerView)

        // --- For Flairs, in a future implementation --- //
        // val flairs: MutableList<Flair> = getFlairsFromDatabase(card.flairIds)
        // FlairView = FlairEditor(this, flairs, binding.FlairView)
    }

    companion object {
        lateinit var card: Card
        lateinit var listener: EditCardListener

        fun OpenEditCard(context: Context, _card: Card, _deck: Deck, _listener: EditCardListener) {
            val intent = Intent(context, EditCardPageActivity::class.java)
            card = _card
            listener = _listener
            intent.putExtra("titleName", "Edit Card")
            intent.putExtra("submitButtonName", "Save Card")
            context.startActivity(intent)
        }

        fun OpenAddCard(context: Context, _listener: EditCardListener) {
            val intent = Intent(context, EditCardPageActivity::class.java)
            card = Card(
                "", // Do you want to generate a new UUID here?
                "",
                "",
                mutableListOf()
            )
            listener = _listener
            intent.putExtra("titleName", "Add Card")
            intent.putExtra("submitButtonName", "Submit Card")
            context.startActivity(intent)
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
        return view.findViewById<EditText>(R.id.CardInput).text.toString()
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





