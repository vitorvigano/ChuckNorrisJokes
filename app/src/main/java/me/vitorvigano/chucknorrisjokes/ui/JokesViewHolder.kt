package me.vitorvigano.chucknorrisjokes.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.jokes_list_item.view.*
import me.vitorvigano.chucknorrisjokes.domain.Joke

class JokesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(joke: Joke) {
        with(itemView) {
            value.text = joke.value
        }
    }
}