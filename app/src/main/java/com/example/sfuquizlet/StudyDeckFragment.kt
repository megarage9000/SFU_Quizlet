package com.example.sfuquizlet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudyDeckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudyDeckFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_study_deck, container, false)
        val addCardButton = view.findViewById<Button>(R.id.AddNewCard)
        val editCardButton = view.findViewById<ImageView>(R.id.EditCard)

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StudyDeckFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudyDeckFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}