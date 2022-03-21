package com.example.sfuquizlet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface Remover {
    fun onRemove()
}

// For the flair page
class FlairViewReycler(val array: ArrayList<String>, context: Context, layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<FlairViewReycler.FlairViewHolder>(){

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
        text.text = array[position]
        view.setOnClickListener {
            array.removeAt(position)
            this.notifyDataSetChanged()
        }

    }
    override fun getItemCount() = array.size

    fun addItem(flairName: String) {
        array.add(flairName)
        notifyItemInserted(array.size - 1);
    }

    fun getFlairItems(): ArrayList<String> {
        return array;
    }

}