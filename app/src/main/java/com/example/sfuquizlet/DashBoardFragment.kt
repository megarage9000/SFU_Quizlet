package com.example.sfuquizlet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sfuquizlet.database.getUserFavourites
import com.example.sfuquizlet.database.getUserFromDatabase
import com.example.sfuquizlet.databinding.DashboardBinding
import com.example.sfuquizlet.recyclerviews.CardDeckViewListener
import com.example.sfuquizlet.recyclerviews.ColorPairing


class DashBoardFragment : Fragment(), CardDeckViewListener, DecksListener{

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
//        MainActivity().getUserFavourites()
        var testingArr: ArrayList<Any> = getUserFavourites()

        for(i in testingArr){
            Log.d("dashboard3", i.toString().substringAfter("value = "))
        }
    }
}