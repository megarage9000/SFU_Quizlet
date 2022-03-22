package com.example.sfuquizlet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sfuquizlet.databinding.CourseListBinding
import com.example.sfuquizlet.recyclerviews.CoursesListRecyclerView


class CoursesListFragment : Fragment(), DecksListener {

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

    override fun onDecksArrived(allDecks: Map<String, List<Deck>>) {
        dialog.hide()
        val context = this.requireContext()
        binding.coursesRecycler.layoutManager = LinearLayoutManager(context)
        binding.coursesRecycler.adapter = CoursesListRecyclerView(allDecks, context)
    }


}