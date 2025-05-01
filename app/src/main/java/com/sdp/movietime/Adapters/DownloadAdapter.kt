package com.sdp.movietime.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.DataClass.RMovie
import com.sdp.movietime.R

class DownloadAdapter(private val downloadedMovies: List<RMovie>) :
    RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_download_movie, parent, false)
        return DownloadViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        val movie = downloadedMovies[position]
        holder.movieName.text = movie.name
        holder.moviePoster.setImageResource(movie.imageRes)
    }

    override fun getItemCount() = downloadedMovies.size

    class DownloadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.moviePosterDownload)
        val movieName: TextView = view.findViewById(R.id.movieNameDownload)
        val downloadIcon: ImageView = view.findViewById(R.id.downloadIcon)
    }
}
