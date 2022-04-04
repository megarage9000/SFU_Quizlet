package com.example.sfuquizlet

import android.app.Activity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sfuquizlet.database.*
import com.example.sfuquizlet.databinding.DashboardBinding
import com.example.sfuquizlet.recyclerviews.CardDeckViewListener
import com.example.sfuquizlet.recyclerviews.ColorPairing
import com.example.sfuquizlet.recyclerviews.CoursesListRecyclerView
//import com.example.sfuquizlet.recyclerviews.FavouriteCoursesRecycler


class DashBoardFragment : Fragment(), CardDeckViewListener, DecksListener, DashboardInfoListener, UserFavouritesListener{

    lateinit var binding: DashboardBinding
    lateinit var userDecks : MutableList<String>
    lateinit var dialog: LoadingDialog
    var favArr = arrayListOf<String>()

    //Need to make sure to load items before showing - Thanks John
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
        binding = DashboardBinding.inflate(inflater)
        val view = binding.root

        //Listeners to update data
        getAllDecksFromDatabase(this)
        getUserCardsPracticed(this)
        getCardsAdded(this)
        getUserFavourites(this)
        getNewCardsToday(this)
        getUserFavouritesString(this)
        //Update new cards num
        val newCardsNum = view.findViewById<TextView>(R.id.newCardNum)

        return view
    }

    companion object {

    }

    //Onclick for course card
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


    override fun onDecksArrived(deck: Map<String, List<Deck>>) {

    }

    //Updates the total cards practiced
    override fun onReceivedCardsPracticed(i: Int) {
        dialog.hide()
        val view = binding.root
        val totalCardsPracticed = view.findViewById<TextView>(R.id.totalPracNum)
        totalCardsPracticed.text = i.toString()

    }

    //Updates total cards added
    override fun onReceivedCardsAdded(i: Int) {
        dialog.hide()
        val view = binding.root
        val totalCardsAdded = view.findViewById<TextView>(R.id.textView5)
        totalCardsAdded.text = i.toString()
    }

    //Updates the favourites course list
    override fun onReceivedUserFavourites(inputDecks: Map<String,List<Deck>>) {
        dialog.hide()
        //Setting up the recycler with the favourite course
        val context = this.requireContext()
        binding.favouritesRecycler.layoutManager = LinearLayoutManager(context)
        binding.favouritesRecycler.adapter = CoursesListRecyclerView(inputDecks, favArr,this, context)
    }

    override fun onReceivedNewCardsToday(i: Int) {
        val view = binding.root
        val newCardsToday = view.findViewById<TextView>(R.id.newCardNum)
        newCardsToday.text = i.toString() + " Cards"
    }

    override fun onReceivedFavouritesString(incomingArr: ArrayList<String>) {
        favArr = incomingArr
//        Log.d("decks2", favArr.toString())
    }
}