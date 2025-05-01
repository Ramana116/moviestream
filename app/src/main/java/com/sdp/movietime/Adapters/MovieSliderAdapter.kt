package com.sdp.movietime.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.DataClass.Movie
import com.sdp.movietime.R

class MovieSliderAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieSliderAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieImage: ImageView = view.findViewById(R.id.movieImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_slider, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieImage.setImageResource(movies[position].imageResId)
    }

    override fun getItemCount() = movies.size
}
