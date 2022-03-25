package com.example.sfuquizlet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.HashMap

interface Remover {
    fun onRemove()
}

// For the flair page
class FlairViewRecycler(val flairs: MutableList<Flair>, context: Context, layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<FlairViewRecycler.FlairViewHolder>(){

    class FlairViewHolder(val view: View, pos: Int) : RecyclerView.ViewHolder(view){
        var pos : Int
            get() {
                return pos
            }
            set(value) {
                pos = value
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlairViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.flair_button, parent, false)
        return FlairViewHolder(view, 0)
    }

    override fun onBindViewHolder(holder: FlairViewHolder, position: Int) {
        val view = holder.itemView.findViewById<View>(R.id.FlairDelete)
        val text = holder.itemView.findViewById<TextView>(R.id.FlairText)
        text.text = flairs[position].title
        view.setOnClickListener {
            flairs.removeAt(position)
            this.notifyDataSetChanged()
        }

    }
    override fun getItemCount() = flairs.size

    fun addItem(flairName: String) {
        val id = UUID.randomUUID().toString()
        flairs.add(Flair(id, flairName))
        notifyItemInserted(flairs.size - 1);
    }

    fun getFlairItems(): MutableList<Flair> {
        return flairs;
    }

}