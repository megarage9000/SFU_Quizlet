package com.example.sfuquizlet

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/*
    For the Edit / Add Card sections, composed of recycler views
    - 2 square input fields for the question / answer
    - 1 FlairViewRecycler for the flair inputs
 */

class EditCardRecycler (val array: Array<EditCardHelperClass>,
                        context: Context,
                        layoutInflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {
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
            var charCounter = this.itemView.findViewById<TextView>(R.id.CharacterCount)
            body.filters = arrayOf(InputFilter.LengthFilter(charLimit))
            body.setText(content)
            charCounter.text = "${body.text.length}/$charLimit"
            body.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        return
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        return
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        var numChars = body.text.length;
                        charCounter.text = "$numChars/$charLimit"
                    }
                }
            )
        }
    }

    class FlairEditorHolder(val view : View, val context: Context)
        : RecyclerView.ViewHolder(view) {

        fun setFlairList(flairs : ArrayList<String>) {
            val flairList = this.itemView.findViewById<RecyclerView>(R.id.FlairList)
            flairList.layoutManager = object : StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }

                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            flairList.adapter = FlairViewReycler(flairs, context, LayoutInflater.from(context))
        }

        fun setInput() {
            val inputField = this.itemView.findViewById<EditText>(R.id.FlairNameInput)
            val submit = this.itemView.findViewById<TextView>(R.id.FlairSubmit)
            val flairList = this.itemView.findViewById<RecyclerView>(R.id.FlairList)
            submit.setOnClickListener {
                Log.d("Flair add", "adding " + inputField.text.toString())
                (flairList.adapter as FlairViewReycler).addItem(
                    inputField.text.toString()
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == array.size - 1){
            return flairId;
        }
        return editFieldID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            editFieldID -> {
                val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    800
                )
                val view = inflator.inflate(R.layout.fill_in_card, parent, false)
                view.layoutParams = layoutParams
                return FillInHolder(view, 250)
            }
            else -> {
                val inflator = LayoutInflater.from(parent.context)
                val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val view = inflator.inflate(R.layout.flair_input, parent, false)
                view.layoutParams = layoutParams
                return FlairEditorHolder(view, parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            editFieldID -> {
                val fillInHolder = (holder as FillInHolder)
                fillInHolder.setContent(array[position].text)
                fillInHolder.setTitle(array[position].title)
            }
            else -> {
                val editFlairHolder = (holder as FlairEditorHolder)
                editFlairHolder.setFlairList(array[position].textArray)
                editFlairHolder.setInput()
            }
        }

    }

    override fun getItemCount(): Int = array.size
}