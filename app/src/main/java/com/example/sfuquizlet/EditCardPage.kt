package com.example.sfuquizlet

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfuquizlet.databinding.ActivityEditCardPageBinding

lateinit var binding : ActivityEditCardPageBinding
class EditCardPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCardPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var test = arrayOf(
            ViewHelperClass("Edit Question", "Enter text", emptyArray<String>()),
            ViewHelperClass("Edit Card", "Enter text", emptyArray<String>())
            )

        var recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EditCardRecycler(test, this, layoutInflater)
    }
}

data class ViewHelperClass(val title: String, var text: String, var textArray: Array<String>)


// For the whole view
class EditCardRecycler(val array: Array<ViewHelperClass>,
                       context: Context,
                       layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {
    val flairId = 0;
    val editFieldID = 1;

    class FillInHolder(val view : View, val charLimit: Int)
        : RecyclerView.ViewHolder(view) {

            fun setTitle(title: String) {
                var titleText = this.itemView.findViewById<TextView>(R.id.CardTitle)
                titleText.setText(title)
            }

            fun setContent(content: String) {
                var body = this.itemView.findViewById<EditText>(R.id.CardInput)
                body.filters = arrayOf(InputFilter.LengthFilter(charLimit))
                body.setText(content)
            }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == itemCount - 1){
            return flairId;
        }
        return editFieldID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            800
        )
        val view = inflator.inflate(R.layout.fill_in_card, parent, false)
        view.layoutParams = layoutParams
        return FillInHolder(view, 250)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val fillInHolder = (holder as FillInHolder)
        fillInHolder.setContent(array[position].text)
        fillInHolder.setTitle(array[position].title)
    }

    override fun getItemCount(): Int = array.size
}


