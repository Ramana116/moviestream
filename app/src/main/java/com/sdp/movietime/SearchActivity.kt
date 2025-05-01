package com.sdp.movietime

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.Adapters.MoviesAdapter
import com.sdp.movietime.DataClass.RMovie

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var allMovies: List<RMovie>  // List containing both recommended and watch again movies

    private val filteredMovies = mutableListOf<RMovie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText)
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView)

        // Get movies from the intent (both recommended and watch again lists)
        allMovies = intent.getParcelableArrayListExtra("MOVIE_LIST") ?: emptyList()

        // Set up RecyclerView
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(this, filteredMovies)
        searchResultsRecyclerView.adapter = moviesAdapter

        // Add search functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterMovies(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterMovies(query: String) {
        // Clear previous search results
        filteredMovies.clear()

        // Filter the list based on the query
        filteredMovies.addAll(allMovies.filter { it.name.contains(query, ignoreCase = true) })

        // If no results found, you can show a toast or any other fallback mechanism
        if (filteredMovies.isEmpty() && query.isNotEmpty()) {
            Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show()
        }

        // Notify the adapter that the data has changed
        moviesAdapter.notifyDataSetChanged()
    }
}
