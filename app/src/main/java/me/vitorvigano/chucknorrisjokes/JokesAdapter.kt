package me.vitorvigano.chucknorrisjokes

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class JokesAdapter(private val jokes: List<Joke>) : RecyclerView.Adapter<JokesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        JokesViewHolder(parent.inflate(R.layout.jokes_list_item))

    override fun getItemCount() = jokes.size

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) =
        holder.bind(jokes[position])
}