package com.example.sfuquizlet

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView

// Recycler View to store all associated decks of a given department
class DepartmentListRecycler(private val departmentCourses: List<Deck>,
                             val context: Context,
                             val layoutInflater: LayoutInflater,
                             val color: Color) : RecyclerView.Adapter<DepartmentListRecycler.DepartmentCourseHolder>(){

    class DepartmentCourseHolder(inflater: LayoutInflater,
                                 parent: ViewParent,
                                 view: View)
        : RecyclerView.ViewHolder(view) {

            fun setContent(deck: Deck, color: Color) {
                val view = this.itemView
                // Set the course number
                view.findViewById<TextView>(R.id.courseTitle).text = deck.courseNumber
                // Set the number of cards
                // - Not sure how to add updated cards
                view.findViewById<TextView>(R.id.courseNumCards).text =
                    "${deck.cardIds.size} Cards"

                // Setting the tint
                // - src: https://www.codegrepper.com/code-examples/java/how+to+change+background+tint+color+programmatically+android
                var drawable: Drawable = view.background
                drawable = DrawableCompat.wrap(drawable)
                DrawableCompat.setTint(drawable, color.toArgb())
                view.background = drawable
            }

            // TODO Add listener to open deck and close deck
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentCourseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.course_card, parent, false)
        return DepartmentCourseHolder(inflater, parent, view)
    }

    override fun onBindViewHolder(holder: DepartmentCourseHolder, position: Int) {
        holder.setContent(
            departmentCourses[position],
            color
        )
    }

    override fun getItemCount(): Int {
        return departmentCourses.size
    }
}