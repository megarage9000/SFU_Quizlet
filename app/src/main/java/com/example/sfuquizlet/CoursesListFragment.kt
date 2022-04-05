package com.example.sfuquizlet

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sfuquizlet.database.UserFavouritesListener
import com.example.sfuquizlet.database.getUserFavouritesString
import com.example.sfuquizlet.databinding.CourseListBinding
import com.example.sfuquizlet.recyclerviews.CardDeckViewListener
import com.example.sfuquizlet.recyclerviews.ColorPairing
import com.example.sfuquizlet.recyclerviews.CoursesListRecyclerView


class CoursesListFragment : Fragment(), DecksListener, CardDeckViewListener, UserFavouritesListener {

    lateinit var binding: CourseListBinding
    lateinit var dialog: LoadingDialog
    lateinit var selectedDeck: Deck
    var favArr = arrayListOf<String>()
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
        getUserFavouritesString(this)
        getAllDecksFromDatabase(this)
        return view
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
        binding.coursesRecycler.adapter = CoursesListRecyclerView(allDecks, favArr, this, context)
    }

    // Listeners for Card Deck Views
    override fun onDeckPressed(department: String, deck: Deck, position: Int, color: ColorPairing) {
        val mainActivity = context as Activity
        val frameLayout = mainActivity.findViewById<FrameLayout>(R.id.frameLayoutID)
        frameLayout.removeAllViews()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutID, StudyDeckFragment.newInstance(
                deck.id,
                deck.department,
                deck.courseNumber,
                deck.cardIds.size,
                deck.cardIds as ArrayList<String>))
            .commit()
    }

    override fun onSavedDeckPressed(arr: ArrayList<String>) {
        addFavouriteDecks(arr)
    }

    override fun onReceivedFavouritesString(incomingArr: ArrayList<String>) {
        favArr = incomingArr
    }
}