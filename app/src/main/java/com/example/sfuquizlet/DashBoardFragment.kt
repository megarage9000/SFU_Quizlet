package com.example.sfuquizlet

import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sfuquizlet.database.*
import com.example.sfuquizlet.databinding.DashboardBinding
import com.example.sfuquizlet.recyclerviews.CardDeckViewListener
import com.example.sfuquizlet.recyclerviews.ColorPairing


class DashBoardFragment : Fragment(), CardDeckViewListener, DecksListener, CardsPracticedListener{

    lateinit var binding: DashboardBinding
    lateinit var userDecks : MutableList<String>
    lateinit var dialog: LoadingDialog

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
        getAllDecksFromDatabase(this)
        getUserCardsPracticed(this)
        getCardsAdded(this)

        //Update new cards num
        val newCardsNum = view.findViewById<TextView>(R.id.newCardNum)

        return view
    }

    companion object {

    }

    override fun onDeckPressed(department: String, deck: Deck, position: Int, color: ColorPairing) {
        //Will add movement to actual deck later
        Log.d("Tapped", department)
    }

    override fun onFavouritesPressed(deck: Deck) {

    }


    override fun onDecksArrived(deck: Map<String, List<Deck>>) {
        dialog.hide()
        val context = this.requireContext()

    }

    //Updates the total cards practiced
    override fun onReceivedCardsPracticed(i: Int) {
        dialog.hide()
        val view = binding.root
        val totalCardsPracticed = view.findViewById<TextView>(R.id.totalPracNum)
        totalCardsPracticed.text = i.toString()

        Log.d("dashzzz", i.toString())
    }

    //Updates total cards added
    override fun onReceivedCardsAdded(i: Int) {
        val view = binding.root
        val totalCardsAdded = view.findViewById<TextView>(R.id.textView5)
        totalCardsAdded.text = i.toString()
    }

    //Updates the favourites course list
    override fun onReceivedUserFavourites(a: ArrayList<String>) {
        //update recycler views
    }
}