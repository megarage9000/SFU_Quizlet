package com.example.sfuquizlet


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfuquizlet.database.getCardsFromDatabase

const val ARG_ID = "id"
const val ARG_DEPARTMENT = "department"
const val ARG_COURSE_NUMBER = "courseNumber"
const val ARG_CARD_COUNT = "cardCount"
const val ARG_CARD_IDS = "cardIds"

class StudyDeckFragment : Fragment() {
    private var id: String? = null
    private var department: String? = null
    private var courseNumber: String? = null
    private var cardCount: Int? = null
    private var cardIds: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
            department = it.getString(ARG_DEPARTMENT)
            courseNumber = it.getString(ARG_COURSE_NUMBER)
            cardCount = it.getInt(ARG_CARD_COUNT)
            cardIds = it.getStringArrayList(ARG_CARD_IDS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_study_deck, container, false)
        val card = inflater.inflate(R.layout.card, container, false)

        val title = view.findViewById<TextView>(R.id.studyDeckTitle)
        title.text = "$department $courseNumber"

        val cardAmount = view.findViewById<TextView>(R.id.studyDeckCardCount)
        cardAmount.text = "$cardCount Cards"

        val addCardButton = view.findViewById<Button>(R.id.AddNewCard)
        val editCardButton = card.findViewById<ImageView>(R.id.edit_button)

        val cardsRecyclerView = view.findViewById<RecyclerView>(R.id.cards_recycler_view)
        val cards = mutableListOf<Card>()
        val cardsRecyclerViewAdapter = CardsRecyclerViewAdapter(cards)
        val llm = LinearLayoutManager(inflater.context)
        cardsRecyclerView.adapter = cardsRecyclerViewAdapter
        cardsRecyclerView.layoutManager = llm
        getCardsFromDatabase(cardIds as MutableList<String>, cardsRecyclerViewAdapter)

        addCardButton.setOnClickListener {
            // John: This was auto filled, I kind of don't know what this is
            // - But it null checks the context
//            container?.let { it1 -> EditCardPageActivity.OpenAddCard(it1.context,
//                "4f47a3bf-3686-4ef0-99ed-cc5550d5b76b") }
        }

        editCardButton.setOnClickListener {
            // John: This was auto filled, I kind of don't know what this is
            // - But it null checks the context
//            container?.let { it1 -> EditCardPageActivity.OpenEditCard(it1.context,
//                "00006e51-058b-4a59-b5e7-bbbdc9ab00a4",
//                "4f47a3bf-3686-4ef0-99ed-cc5550d5b76b") }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String, department: String, courseNumber: String, cardCount: Int, cardIds: ArrayList<String>) =
            StudyDeckFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                    putString(ARG_DEPARTMENT, department)
                    putString(ARG_COURSE_NUMBER, courseNumber)
                    putInt(ARG_CARD_COUNT, cardCount)
                    putStringArrayList(ARG_CARD_IDS, cardIds)
                }
            }
    }
}

class CardsRecyclerViewAdapter(var cards: MutableList<Card>) : RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionTextView: TextView = view.findViewById<TextView>(R.id.question)
        val authorTextView: TextView = view.findViewById<TextView>(R.id.author)
        val editButton: ImageButton = view.findViewById<ImageButton>(R.id.edit_button)
        val deleteButton: ImageButton = view.findViewById<ImageButton>(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val card = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(card)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("SFUQuizlet", "Bind new card")
        var card = cards[position]
        holder.questionTextView.text = card.question
        holder.authorTextView.text = "Author: ${card.authorId}"
    }

    override fun getItemCount(): Int = cards.size

    fun addCard(card: Card) {
        cards.add(card)
        notifyDataSetChanged()
    }
}