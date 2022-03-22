package com.example.sfuquizlet.recyclerviews

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sfuquizlet.Deck
import com.example.sfuquizlet.R

// Recycler View to store all associated decks of a given department
class DepartmentListRecycler(private val department: String,
                            private val departmentCourses: List<Deck>,
                             val context: Context,
                             val layoutInflater: LayoutInflater,
                             val color: Int) : RecyclerView.Adapter<DepartmentListRecycler.DepartmentCourseHolder>(){

    class DepartmentCourseHolder(
        private val department: String,
        inflater: LayoutInflater,
        parent: ViewParent,
        view: View)
        : RecyclerView.ViewHolder(view) {

            fun setContent(deck: Deck, color: Int) {
                val view = this.itemView
                // Set the course number
                val title = view.findViewById<TextView>(R.id.courseTitle)
                title.text = department + deck.courseNumber
                // Set the number of cards
                // - Not sure how to add updated cards
                val numCards = view.findViewById<TextView>(R.id.courseNumCards)
                numCards.text =
                    "${deck.cardIds.size} Cards"



                // Setting the tint for background
                // - src: https://www.codegrepper.com/code-examples/java/how+to+change+background+tint+color+programmatically+android
                var drawable = DrawableCompat.wrap(view.background)
                DrawableCompat.setTint(drawable, color)
                view.background = drawable


                // The favourite button
                val favButton = view.findViewById<Button>(R.id.favouriteButton)

                // Darken the color of the text, button
                val darkerColor = darkenColour(color)
                title.setTextColor(darkerColor)
                numCards.setTextColor(darkerColor)

                // Setting the tint for the favourite button background
                drawable = DrawableCompat.wrap(favButton.background)
                DrawableCompat.setTint(drawable, darkerColor)
                favButton.background = drawable
            }
            // TODO Add listener to open deck and close deck
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentCourseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.course_card, parent, false)
        return DepartmentCourseHolder(department, inflater, parent, view)
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

// Darkens a given color:
// - https://stackoverflow.com/questions/33072365/how-to-darken-a-given-color-int
fun darkenColour(color: Int) : Int {
    return ColorUtils.blendARGB(color, Color.BLACK, 0.5f)
}
