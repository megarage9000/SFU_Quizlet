package com.example.sfuquizlet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sfuquizlet.databinding.ActivityEditCardPageBinding

lateinit var binding : ActivityEditCardPageBinding
class EditCardPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCardPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val passedParams = intent.extras
        val titleName = intent.getStringExtra("titleName")
        val submitButtonName = intent.getStringExtra("submitButtonName")
        val flairs = intent.getStringArrayListExtra("flairs")

        binding.Title.text = titleName
        binding.SubmitButton.text = submitButtonName

        var test = arrayOf(
            EditCardHelperClass("Question", "Enter text", ArrayList<String>()),
            EditCardHelperClass("Answer", "Enter text", ArrayList<String>()),
            EditCardHelperClass("Flairs", "Enter Flair", flairs!!)
        )

        var recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EditCardRecycler(test, this, layoutInflater)
    }
}
data class EditCardHelperClass(val title: String, var text: String, var textArray: ArrayList<String>)





