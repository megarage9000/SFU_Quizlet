package com.example.sfuquizlet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sfuquizlet.databinding.CourseListBinding


class CoursesListFragment : Fragment(), DecksListener {

    lateinit var binding: CourseListBinding

    lateinit var dialog: LoadingDialog

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