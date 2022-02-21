package com.example.sfuquizlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sfuquizlet.databinding.ActivityEditCardPageBinding

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





