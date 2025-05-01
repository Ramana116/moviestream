package com.sdp.movietime

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sdp.movietime.Adapters.DownloadAdapter
import com.sdp.movietime.DataClass.RMovie

class DownloadActivity : AppCompatActivity() {

    private lateinit var downloadedMoviesRecyclerView: RecyclerView
    private lateinit var downloadAdapter: DownloadAdapter
    private  lateinit var bottomNavigationView: BottomNavigationView
    private val downloadedMovies = mutableListOf<RMovie>() // List to store downloaded movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        downloadedMoviesRecyclerView = findViewById(R.id.downloadedMoviesRecyclerView)
        downloadedMoviesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        downloadAdapter = DownloadAdapter(downloadedMovies)
        downloadedMoviesRecyclerView.adapter = downloadAdapter

        // Load downloaded movies from SharedPreferences
        loadDownloadedMovies()


        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Handle Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_wishlist -> {
                    startActivity(Intent(this, WishlistActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }

    }

    private fun loadDownloadedMovies() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MOVIE_DOWNLOADS", MODE_PRIVATE)
        val movieSet = sharedPreferences.getStringSet("DOWNLOADED_MOVIES", mutableSetOf())

        downloadedMovies.clear()

        movieSet?.forEach { movieData ->
            val parts = movieData.split("|")
            if (parts.size == 2) {
                val movieName = parts[0]
                val movieImage = parts[1].toInt()
                downloadedMovies.add(RMovie(movieName, movieImage, "", ""))
            }
        }

        // Notify adapter about changes
        downloadAdapter.notifyDataSetChanged()
    }
}
