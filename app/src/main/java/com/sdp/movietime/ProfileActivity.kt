package com.sdp.movietime

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var profileNameTextView: TextView
    private lateinit var btnDeleteAccount: TextView
    private lateinit var updateProfileButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var profileToolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize UI components
        profileImage = findViewById(R.id.profileImage)
        profileNameTextView = findViewById(R.id.profileTextView)
        btnDeleteAccount = findViewById(R.id.removeProfile)
        updateProfileButton = findViewById(R.id.updateProfile)
        profileToolbar = findViewById(R.id.profileToolbar)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set up Toolbar with Back Button
        setSupportActionBar(profileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("profile_name", "DefaultUsername")

        profileNameTextView.text = savedUsername ?: "No name available"

        // Update Profile Button Click Listener
        updateProfileButton.setOnClickListener {
            val intent = Intent(this, UpdateProfileActivity::class.java)
            intent.putExtra("profile_name", savedUsername )
            startActivity(intent)
        }

        // Delete Account Click Listener
        btnDeleteAccount.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        // Bottom Navigation Handling
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
                R.id.nav_profile -> true
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }
    override fun onResume() {
        super.onResume()

        // ✅ Get the latest username from SharedPreferences
        val savedUsername = sharedPreferences.getString("profile_name", "Username Not Found")
        profileNameTextView.text = savedUsername
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Yes") { _, _ ->
                deleteUser()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteUser() {
        val username = sharedPreferences.getString("profile_name", null)
        if (username.isNullOrEmpty()) {
            Toast.makeText(this, "Error: Username not found", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulate API call to delete user (replace with actual API)
        Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
        sharedPreferences.edit().clear().apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
