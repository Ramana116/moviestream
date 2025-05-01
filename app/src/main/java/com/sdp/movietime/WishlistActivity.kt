package com.sdp.movietime

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sdp.movietime.Adapters.WishlistAdapter

class WishlistActivity : AppCompatActivity() {
    private lateinit var wishlistAdapter: WishlistAdapter
    private val wishlistMovies = mutableListOf<String>()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val recyclerView: RecyclerView = findViewById(R.id.wishlistRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadWishlistMovies()

        wishlistAdapter = WishlistAdapter(wishlistMovies) { movieTitle ->
            removeMovieFromWishlist(movieTitle)
        }

        recyclerView.adapter = wishlistAdapter
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

    private fun loadWishlistMovies() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MOVIE_APP", MODE_PRIVATE)
        val savedMovies = sharedPreferences.getStringSet("WISHLIST_MOVIES", mutableSetOf()) ?: mutableSetOf()

        wishlistMovies.clear()
        wishlistMovies.addAll(savedMovies)
    }

    private fun removeMovieFromWishlist(movieTitle: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MOVIE_APP", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val wishlistMovies = sharedPreferences.getStringSet("WISHLIST_MOVIES", mutableSetOf()) ?: mutableSetOf()
        wishlistMovies.remove(movieTitle)

        editor.putStringSet("WISHLIST_MOVIES", wishlistMovies)
        editor.apply()

        // Update UI
        this.wishlistMovies.remove(movieTitle)
        wishlistAdapter.notifyDataSetChanged()

        Toast.makeText(this, "$movieTitle removed from Wishlist", Toast.LENGTH_SHORT).show()
    }
}
