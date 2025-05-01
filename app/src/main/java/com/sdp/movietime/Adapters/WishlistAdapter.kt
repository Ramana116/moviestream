package com.sdp.movietime.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.R

class WishlistAdapter(
    private val wishlistMovies: MutableList<String>,
    private val onRemoveClick: (String) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    class WishlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.findViewById(R.id.wishlistMovieTitle)
        val removeButton: ImageView = view.findViewById(R.id.removeWishlistButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val movieTitle = wishlistMovies[position]
        holder.movieTitle.text = movieTitle

        holder.removeButton.setOnClickListener {
            onRemoveClick(movieTitle)
        }
    }

    override fun getItemCount(): Int = wishlistMovies.size
}
