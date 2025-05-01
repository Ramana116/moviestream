package com.sdp.movietime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdp.movietime.Adapters.MoviesGenreAdapter
import com.sdp.movietime.DataClass.MovieG

class GenreMoviesActivity : AppCompatActivity() {

    private lateinit var genreMoviesRecyclerView: RecyclerView
    private lateinit var genreMoviesAdapter: MoviesGenreAdapter
    private lateinit var selectedGenre: String

    private val allMovies = listOf(
        MovieG("Avengers: Endgame", R.drawable.endgame, "Action"),
        MovieG("Spider-Man: No Way Home", R.drawable.spiderman, "Action"),
        MovieG("Titanic", R.drawable.titanic, "Romance"),
        MovieG("Inception", R.drawable.inception, "Sci-Fi"),
        MovieG("The Dark Knight", R.drawable.dark_knight, "Action"),
        MovieG("Interstellar", R.drawable.interstellar, "Sci-Fi"),
        MovieG("God Father",R.drawable.godfather, "Drama")
        // Add more movies here with their genres
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_movies)

        // Get selected genre from intent
        selectedGenre = intent.getStringExtra("SELECTED_GENRE") ?: ""

        // Filter movies based on selected genre
        val filteredMovies = allMovies.filter { it.genre == selectedGenre }

        // Set up RecyclerView to display the filtered movies
        genreMoviesRecyclerView = findViewById(R.id.genreMoviesRecyclerView)
        genreMoviesRecyclerView.layoutManager = LinearLayoutManager(this)
        genreMoviesAdapter = MoviesGenreAdapter(this, filteredMovies)
        genreMoviesRecyclerView.adapter = genreMoviesAdapter
    }
}
