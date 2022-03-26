package com.example.sfuquizlet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sfuquizlet.databinding.CourseListBinding
import com.example.sfuquizlet.recyclerviews.CardDeckViewListener
import com.example.sfuquizlet.recyclerviews.ColorPairing
import com.example.sfuquizlet.recyclerviews.CoursesListRecyclerView


class CoursesListFragment : Fragment(), DecksListener, CardDeckViewListener, EditCardListener {

    lateinit var binding: CourseListBinding
    lateinit var dialog: LoadingDialog
    lateinit var selectedDeck: Deck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = LoadingDialog(requireContext())
        dialog.show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = CourseListBinding.inflate(inflater)
        val view = binding.root
        getAllDecksFromDatabase(this)
        return view
    }

    companion object {

    }

    // Listener to await all the retrieved decks from the database
    // - Will begin its wait when getAllDecksFromDatabase() is called
    override fun onDecksArrived(allDecks: Map<String, List<Deck>>) {
        dialog.hide()
        val context = this.requireContext()
        binding.coursesRecycler.layoutManager = LinearLayoutManager(context)

        // Creating the recycler view for course list
        // - Note that we are passing this as a viewListener to check
        // if any of the views are clicked and / or if they are favourite'd
        binding.coursesRecycler.adapter = CoursesListRecyclerView(allDecks, this, context)
    }

    // Listeners for Card Deck Views
    override fun onDeckPressed(department: String, deck: Deck, position: Int, color: ColorPairing) {
        Log.d("Got Deck: ", deck.toString())
        Log.d("Deck Department: ", department)
        Log.d("Deck Position: ", position.toString())
        EditCardPageActivity.OpenAddCard(this.requireContext(), this)
    }

    override fun onFavouritesPressed(deck: Deck) {
        Log.d("Got Deck for favourite: ", deck.toString())
    }

    override fun onEditCardClose(card: Card) {
        Log.d("New card Added!: ", card.toString())
    }


}