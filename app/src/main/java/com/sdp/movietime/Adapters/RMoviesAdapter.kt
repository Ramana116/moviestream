package com.sdp.movietime.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.DataClass.RMovie
import com.sdp.movietime.MovieDetailActivity
import com.sdp.movietime.R

class RMoviesAdapter(private val movies: List<RMovie>) :
    RecyclerView.Adapter<RMoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView = view.findViewById(R.id.movieName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieName.text = movie.name

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE_NAME", movie.name)
                putExtra("MOVIE_DESCRIPTION", movie.description)
                putExtra("MOVIE_GENRE", movie.genres)
                putExtra("MOVIE_TRAILER", R.raw.dark_knight) // Assign correct trailer ID
                putExtra("MOVIE_IMAGE", movie.imageRes)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = movies.size
}
