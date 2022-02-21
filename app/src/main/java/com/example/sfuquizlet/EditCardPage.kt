package com.example.sfuquizlet

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sfuquizlet.databinding.ActivityEditCardPageBinding
import java.io.Console

lateinit var binding : ActivityEditCardPageBinding
class EditCardPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCardPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var test = arrayOf(
            EditCardHelperClass("Edit Question", "Enter text", ArrayList<String>()),
            EditCardHelperClass("Edit Card", "Enter text", ArrayList<String>()),
            EditCardHelperClass("Flairs", "Enter Flair", ArrayList<String>(arrayListOf("Midterm 1", "Quiz", "Neuroptics")))
        )

        var recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EditCardRecycler(test, this, layoutInflater)
    }
}
data class EditCardHelperClass(val title: String, var text: String, var textArray: ArrayList<String>)





