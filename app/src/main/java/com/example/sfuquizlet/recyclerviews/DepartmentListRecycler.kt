package com.example.sfuquizlet.recyclerviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sfuquizlet.Deck
import com.example.sfuquizlet.R
import com.example.sfuquizlet.addFavouriteDecks

// Interface to call events on the card deck views
interface CardDeckViewListener {
    fun onDeckPressed(department: String, deck: Deck, position: Int, color: ColorPairing)

    // Modify this if needed for favourites
//    fun onSavedDeckPressed(deck: Deck)
    fun onSavedDeckPressed(arr: ArrayList<String>)
}


// Recycler View to store all associated decks of a given department
class DepartmentListRecycler(private val department: String,
                             private val departmentCourses: List<Deck>,
                             private val favouriteCourses: ArrayList<String>,
                             private val viewListener: CardDeckViewListener,
                             val context: Context,
                             val color: ColorPairing) : RecyclerView.Adapter<DepartmentListRecycler.DepartmentCourseHolder>(){

    // This view holder is specific to course_card.xml
    class DepartmentCourseHolder(
        private val department: String,
        view: View)
        : RecyclerView.ViewHolder(view) {

            fun setData(color: ColorPairing, deck: Deck, favDeck: ArrayList<String>, viewListener: CardDeckViewListener, position: Int) {

                val view = this.itemView
                // Set the view on click listener
                view.setOnClickListener {
                    viewListener.onDeckPressed(department, deck, position, color)
                }

                // Set the course number
                val title = view.findViewById<TextView>(R.id.courseTitle)
                title.text = department + " " + deck.courseNumber

                // Set the number of cards
                // - Not sure how to add updated cards
                val numCards = view.findViewById<TextView>(R.id.courseNumCards)
                numCards.text =
                    "${deck.cardIds.size} Cards"

                // Setting the tint for background
                // - src: https://www.codegrepper.com/code-examples/java/how+to+change+background+tint+color+programmatically+android
                var drawable = DrawableCompat.wrap(view.background)
                DrawableCompat.setTint(drawable, color.primary)
                view.background = drawable


                // The save button
                val saveButton = view.findViewById<ToggleButton>(R.id.favouriteButton)
                for(decks in favDeck){
                    if(decks == deck.id) saveButton.isChecked = true
                }

                // Set the on favourite button listener
                saveButton.setOnClickListener {
                    var favDeckHolder: ArrayList<String> = favDeck
                    if(!favDeckHolder.contains(deck.id)) favDeckHolder.add(deck.id)
                    else if(favDeck.contains(deck.id)) favDeckHolder.remove(deck.id)
                    viewListener.onSavedDeckPressed(favDeckHolder)
                }

                // Darken the color of the text, button
                val darkerColor = color.secondary
                title.setTextColor(darkerColor)
                numCards.setTextColor(darkerColor)

                val bookmark = view.findViewById<ToggleButton>(R.id.favouriteButton)
                bookmark.setBackgroundTintList(ColorStateList.valueOf(darkerColor));
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentCourseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.course_card, parent, false)
        return DepartmentCourseHolder(department, view)
    }

    override fun onBindViewHolder(holder: DepartmentCourseHolder, position: Int) {
        holder.setData(
            color,
            departmentCourses[position],
            favouriteCourses,
            viewListener,
            position
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
