package com.example.sfuquizlet.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sfuquizlet.Deck
import com.example.sfuquizlet.EditCardPageActivity.Companion.deck
import com.example.sfuquizlet.R


//COULD NOT GET IT TO WORK, CURRENTLY USING JOHN'S RECYCLERVIEWS


class FavouriteCoursesRecycler (private val courseName: String,
                                private val courseDeck: List<Deck>,
                                private val viewListener: CardDeckViewListener,
                                val context: Context,
                                val color:ColorPairing): RecyclerView.Adapter<FavouriteCoursesRecycler.FavouriteCoursesHolder>(){

    class FavouriteCoursesHolder(private val department: String, view: View): RecyclerView.ViewHolder(view){

        fun setData(color: ColorPairing, deck:Deck, viewListener: CardDeckViewListener, position: Int){

            //Credit: From John's recyclerViews
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
            numCards.text = "${deck.cardIds.size} Cards"

            // Setting the tint for background
            // - src: https://www.codegrepper.com/code-examples/java/how+to+change+background+tint+color+programmatically+android
            var drawable = DrawableCompat.wrap(view.background)
            DrawableCompat.setTint(drawable, color.primary)
            view.background = drawable

            // The favourite button
            val favButton = view.findViewById<Button>(R.id.favouriteButton)

            // Set the on favourite button listener
            favButton.setOnClickListener {
                viewListener.onFavouritesPressed(deck)
            }

            // Darken the color of the text, button
            val darkerColor = color.secondary
            title.setTextColor(darkerColor)
            numCards.setTextColor(darkerColor)

            // Setting the tint for the favourite button background
            drawable = DrawableCompat.wrap(favButton.background)
            DrawableCompat.setTint(drawable, darkerColor)
            favButton.background = drawable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteCoursesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.course_card, parent, false)
        return FavouriteCoursesHolder(courseName,view)
    }

    override fun onBindViewHolder(holder: FavouriteCoursesHolder, position: Int) {
        holder.setData(
            color,
            deck,
            viewListener,
            position
        )
    }

    override fun getItemCount(): Int {
        return courseDeck.size
    }
}