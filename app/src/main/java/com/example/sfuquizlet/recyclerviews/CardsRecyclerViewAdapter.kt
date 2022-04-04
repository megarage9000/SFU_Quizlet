package com.example.sfuquizlet.recyclerviews

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfuquizlet.*

class CardsRecyclerViewAdapter(var deckId: String, var cardIds: MutableList<String>, var cards: MutableList<Card>, val listener: EditCardListener) : RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder>()  {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), EditCardListener {
        private var isDisplayingQuestion = true

        private val questionTextView: TextView = view.findViewById<TextView>(R.id.question)
        private val authorTextView: TextView = view.findViewById<TextView>(R.id.author)
        private val editButton: ImageButton = view.findViewById<ImageButton>(R.id.edit_button)
        private val deleteButton: ImageButton = view.findViewById<ImageButton>(R.id.delete_button)

        private lateinit var card: Card

        fun setData(card: Card) {
            // Populate card contents
            this.card = card
            questionTextView.text = this.card.question
            authorTextView.text = "Added by ${this.card.authorId}"

            // Attach edit button listener
            editButton.setOnClickListener {
                EditCardPageActivity.OpenEditCard(this.itemView.context, this.card, listener)
            }

            // Attach delete button listener
            deleteButton.setOnClickListener {
                // Find index of card from cards
                removeCard(this.card)

                // Remove card from database
                deleteCard(deckId, this.card.id, cardIds)
            }

            // Toggle between question and answer
            val view = this.itemView
            view.setOnClickListener {
                // TODO: Update cards viewed count
                onClickFlipCard()
            }
        }

        override fun onEditCardClose(card: Card) {
            Log.i("Steven Pearce Update", card.toString())
            updateCard(card)
        }

        private fun onClickFlipCard() {
            val view = this.itemView
            val animation1 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f)
            val animation2 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)

            animation1.interpolator = AccelerateDecelerateInterpolator()
            animation1.duration = 100

            animation2.interpolator = AccelerateDecelerateInterpolator()
            animation2.duration = 100

            animation1.addListener(object : AnimatorListenerAdapter() {
                // Once this animation ends, call the next one
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    // If displaying question, switch to answer
                    if (isDisplayingQuestion) {
                        questionTextView.text = card.answer

                        // Else, switch to question
                    } else {
                        questionTextView.text = card.question
                    }

                    // Toggle boolean check
                    isDisplayingQuestion = !isDisplayingQuestion
                    animation2.start()
                }
            })
            animation1.start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val card = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(card)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(cards[position])
    }

    override fun getItemCount(): Int = cards.size

    fun addCard(card: Card) {
        cards.add(0, card)
        cardIds.add(card.id)
        notifyDataSetChanged()
    }

    fun updateCard(card: Card) {
        val index = findCardIndex(card)

        if (index == -1) {
            Log.i("SFUQuizlet", "Unable to update card")
            return
        }

        cards[index] = card


        notifyDataSetChanged()
    }

    fun removeCard(card: Card) {
        val index = findCardIndex(card)

        if (index == -1) {
            Log.i("SFUQuizlet", "Unable to update card")
            return
        }

        cards.removeAt(index)
        cardIds.removeIf { it == card.id }

        notifyDataSetChanged()
    }

    private fun findCardIndex(targetCard: Card): Int {
        for ((index, card) in cards.withIndex()) {
            if (card.id == targetCard.id) {
                return index
            }
        }
        return -1
    }
}