package com.sdp.movietime.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.R

class GenreAdapter(private val context: Context, private val genres: List<String>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    var onGenreClick: ((String) -> Unit)? = null

    class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val genreTextView: TextView = view.findViewById(R.id.genreTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.genreTextView.text = genre
        holder.itemView.setOnClickListener {
            onGenreClick?.invoke(genre)  // Trigger the click event
        }
    }

    override fun getItemCount(): Int = genres.size
}
