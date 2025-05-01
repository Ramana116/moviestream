package com.sdp.movietime.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.DataClass.RMovie
import com.sdp.movietime.MovieDetailActivity
import com.sdp.movietime.R

class MoviesAdapter(private val context: Context, private val movies: List<RMovie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Check if views are properly assigned
        holder.movieName?.text = movie.name
        holder.moviePoster?.setImageResource(movie.imageRes)

        // Set OnClickListener to open MovieDetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("MOVIE_NAME", movie.name)
                putExtra("MOVIE_IMAGE", movie.imageRes)
                putExtra("MOVIE_DESCRIPTION", movie.description)
                putExtra("MOVIE_GENRE", movie.genres)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView? = view.findViewById(R.id.moviePoster)
        val movieName: TextView? = view.findViewById(R.id.movieName)
    }
}
