package com.sdp.movietime

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sdp.movietime.Adapters.GenreAdapter
import com.sdp.movietime.Adapters.MovieSliderAdapter
import com.sdp.movietime.Adapters.MoviesAdapter
import com.sdp.movietime.DataClass.Movie
import com.sdp.movietime.DataClass.RMovie

class DashboardActivity : AppCompatActivity() {

    private lateinit var movieViewPager: ViewPager2
    private lateinit var recommendedMoviesRecyclerView: RecyclerView
    private lateinit var genresRecyclerView: RecyclerView
    private lateinit var userImageView: ImageView
    private lateinit var searchIcon: ImageView
    private lateinit var userNameTextView: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var sliderAdapter: MovieSliderAdapter
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var watchAgainRecyclerView: RecyclerView
    private val sliderHandler = Handler(Looper.getMainLooper())

    private val recommendedMovies = listOf(
        RMovie("The Dark Knight", R.drawable.dark_knight, "When the menace known as the Joker emerges from his mysterious past, he creates chaos in Gotham City.", "Action • Crime • Drama", R.raw.dark_knight),
        RMovie("Inception", R.drawable.inception, "A thief who enters the dreams of others to steal secrets is given a task to plant an idea into a person's mind.", "Sci-Fi • Thriller • Action", R.raw.inception),
        RMovie("Interstellar", R.drawable.interstellar, "A team of explorers travels through a wormhole in space in an attempt to ensure humanity’s survival.", "Sci-Fi • Adventure • Drama", R.raw.interstellar),
        RMovie("Titanic", R.drawable.titanic, "A young couple from different social classes fall in love aboard the ill-fated R.M.S. Titanic.", "Romance • Drama • History", R.raw.titanic)
    )

    private val sliderMovies = listOf(
        Movie("Avengers: Endgame", R.drawable.endgame),
        Movie("Spider-Man: No Way Home", R.drawable.spiderman),
        Movie("Bahubali", R.drawable.telugu)
    )

    private val genres = listOf("Action", "Comedy", "Drama", "Horror", "Sci-Fi", "Romance", "Thriller", "Fantasy", "Adventure")

    // Watch Again section movies
    private val watchAgainMovies = listOf(
        RMovie("The Shawshank Redemption", R.drawable.shawshak, "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", "Drama", R.raw.shawshank),
        RMovie("The Godfather", R.drawable.godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", "Crime • Drama", R.raw.godfather),
        RMovie("The Matrix", R.drawable.matrix, "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", "Action • Sci-Fi • Thriller", R.raw.matrix)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize views
        userImageView = findViewById(R.id.userImageView)
        searchIcon = findViewById(R.id.searchIcon)
        userNameTextView = findViewById(R.id.userNameTextView)
        movieViewPager = findViewById(R.id.movieViewPager)
        recommendedMoviesRecyclerView = findViewById(R.id.recommendedMoviesRecyclerView)
        genresRecyclerView = findViewById(R.id.genresRecyclerView)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        watchAgainRecyclerView = findViewById(R.id.watchAgainRecyclerView)

        // Get the email passed from LoginActivity
        val userEmail = intent.getStringExtra("USER_EMAIL")

        // Set the user's email as the username
        if (userEmail != null) {
            userNameTextView.text = "Welcome, $userEmail"  // Set the email in the TextView
        } else {
            userNameTextView.text = "Welcome, Guest"  // Fallback in case email is not passed
        }

        // Set up ViewPager for movie slider
        sliderAdapter = MovieSliderAdapter(sliderMovies)
        movieViewPager.adapter = sliderAdapter

        // Enable visible previews on the sides
        movieViewPager.clipToPadding = false
        movieViewPager.clipChildren = false
        movieViewPager.offscreenPageLimit = 3

        // Add scaling effect for smooth transition
        movieViewPager.setPageTransformer { page, position ->
            val absPosition = Math.abs(position)
            page.scaleY = 0.85f + (1 - absPosition) * 0.15f
            page.scaleX = 0.85f + (1 - absPosition) * 0.15f
            page.alpha = 0.5f + (1 - absPosition) * 0.5f
        }

        // Auto-slide functionality
        val sliderRunnable = object : Runnable {
            override fun run() {
                movieViewPager.currentItem = (movieViewPager.currentItem + 1) % sliderMovies.size
                sliderHandler.postDelayed(this, 3000) // Change every 3 seconds
            }
        }
        sliderHandler.postDelayed(sliderRunnable, 3000)

        // Set up RecyclerView for recommended movies (Horizontal Scrolling)
        recommendedMoviesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        moviesAdapter = MoviesAdapter(this, recommendedMovies)
        recommendedMoviesRecyclerView.adapter = moviesAdapter

        // Set up RecyclerView for genres (Horizontal Scrolling)
        genresRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        genreAdapter = GenreAdapter(this, genres)

        // Set the click listener for genre selection
        genreAdapter.onGenreClick = { selectedGenre ->
            // Open GenreMoviesActivity and pass the selected genre
            val intent = Intent(this, GenreMoviesActivity::class.java)
            intent.putExtra("SELECTED_GENRE", selectedGenre)
            startActivity(intent)
        }

        genresRecyclerView.adapter = genreAdapter

        // Set up RecyclerView for Watch Again (Horizontal Scrolling)
        watchAgainRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val watchAgainAdapter = MoviesAdapter(this, watchAgainMovies)
        watchAgainRecyclerView.adapter = watchAgainAdapter

        // Set user profile details
        //userNameTextView.text = "John Doe"
        userImageView.setImageResource(R.drawable.ic_user)

        // Search icon click listener
        // Search icon click listener
        searchIcon.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)

            // Pass both recommendedMovies and watchAgainMovies to SearchActivity
            val allMovies = ArrayList<RMovie>()
            allMovies.addAll(recommendedMovies)
            allMovies.addAll(watchAgainMovies)

            intent.putParcelableArrayListExtra("MOVIE_LIST", allMovies)
            startActivity(intent)
        }

        // Handle Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
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
}
