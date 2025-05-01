package com.sdp.movietime

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var wishlistButton: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var title: String
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var playButton: Button // Reference to the play button
    private lateinit var movieTitleImage: ImageView

    private var isPlaying = false // Variable to track the state (playing or paused)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Get views
        val movieTitle: TextView = findViewById(R.id.movieNameDetail)
        val movieDescription: TextView = findViewById(R.id.movieDescription)
        val movieGenres: TextView = findViewById(R.id.movieGenres)
        playButton = findViewById(R.id.playButton) // Initialize play button
        wishlistButton = findViewById(R.id.wishlistButton)
        playerView = findViewById(R.id.playerView)
        movieTitleImage = findViewById(R.id.movieTitleImage) // Initialize movieTitleImageView

        // Retrieve intent data
        title = intent.getStringExtra("MOVIE_NAME") ?: "N/A"
        val description = intent.getStringExtra("MOVIE_DESCRIPTION") ?: "No Description"
        val genres = intent.getStringExtra("MOVIE_GENRE") ?: "Unknown Genre"
        val trailerResId = intent.getIntExtra("MOVIE_TRAILER", R.raw.dark_knight) // Default trailer
        // Inside MovieDetailActivity
        val movieImageResId = intent.getIntExtra("MOVIE_IMAGE", R.drawable.telugu)
        movieTitleImage.setImageResource(movieImageResId)


        // Set data to views
        movieTitle.text = title
        movieDescription.text = description
        movieGenres.text = genres

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MOVIE_APP", MODE_PRIVATE)

        // Initialize ExoPlayer (paused initially)
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        val videoUri = Uri.parse("android.resource://$packageName/$trailerResId")
        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)
        player.prepare()

        // Set Wishlist Button State
        updateWishlistButtonState()

        // Play Button Click Event
        playButton.setOnClickListener {
            if (isPlaying) {
                player.pause() // Pause the video if it's already playing
                isPlaying = false
                playButton.text = "Play" // Update button text to "Play"
                Toast.makeText(this, "Movie paused", Toast.LENGTH_SHORT).show()
            } else {
                player.play() // Play the video if it's paused
                isPlaying = true
                playButton.text = "Pause" // Update button text to "Pause"
                Toast.makeText(this, "Movie is playing...", Toast.LENGTH_SHORT).show()
            }
        }

        // Wishlist Button Click Event (Add/Remove)
        wishlistButton.setOnClickListener {
            toggleWishlist()
        }

        // Handle Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
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

    // Toggle Wishlist Status
    private fun toggleWishlist() {
        val editor = sharedPreferences.edit()
        val wishlistMovies = sharedPreferences.getStringSet("WISHLIST_MOVIES", mutableSetOf()) ?: mutableSetOf()

        if (wishlistMovies.contains(title)) {
            wishlistMovies.remove(title)
            Toast.makeText(this, "$title removed from Wishlist", Toast.LENGTH_SHORT).show()
        } else {
            wishlistMovies.add(title)
            Toast.makeText(this, "$title added to Wishlist", Toast.LENGTH_SHORT).show()
        }

        editor.putStringSet("WISHLIST_MOVIES", wishlistMovies)
        editor.apply()

        updateWishlistButtonState()
    }

    // Update Wishlist Button UI
    private fun updateWishlistButtonState() {
        val wishlistMovies = sharedPreferences.getStringSet("WISHLIST_MOVIES", mutableSetOf()) ?: mutableSetOf()
        if (wishlistMovies.contains(title)) {
            wishlistButton.setImageResource(R.drawable.ic_wishlist) // Change to filled heart icon
        } else {
            wishlistButton.setImageResource(R.drawable.ic_wishlist) // Change to outline heart icon
        }
    }

    override fun onStop() {
        super.onStop()
        player.release() // Release player when activity stops
    }
}
