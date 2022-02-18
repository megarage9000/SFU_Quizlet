package com.example.sfuquizlet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// For the flair page
class FlairViewReycler(val array: Array<String>, context: Context, layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<FlairViewReycler.FlairViewHolder>() {

    class FlairViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlairViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FlairViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = array.size

}