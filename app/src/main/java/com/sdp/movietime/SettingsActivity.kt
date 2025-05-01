package com.sdp.movietime

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val cellularDataUsage = findViewById<TextView>(R.id.cellularDataUsage)
        val wifiOnlySwitch = findViewById<Switch>(R.id.wifiOnlySwitch)
        val videoQuality = findViewById<TextView>(R.id.videoQuality)
        val deleteDownloads = findViewById<TextView>(R.id.deleteDownloads)
        val modeSwitch = findViewById<Switch>(R.id.modeSwitch)

        // Load saved preferences
        wifiOnlySwitch.isChecked = sharedPreferences.getBoolean("WIFI_ONLY", true)
        modeSwitch.isChecked = sharedPreferences.getBoolean("DARK_MODE", true)

        // Cellular Data Usage - Click to change
        cellularDataUsage.setOnClickListener {
            val options = arrayOf("Low", "Medium", "High")
            AlertDialog.Builder(this)
                .setTitle("Select Cellular Data Usage")
                .setItems(options) { _, which ->
                    cellularDataUsage.text = options[which]
                    sharedPreferences.edit().putString("CELLULAR_DATA_USAGE", options[which]).apply()
                }
                .show()
        }

        // Wi-Fi Only Toggle
        wifiOnlySwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            sharedPreferences.edit().putBoolean("WIFI_ONLY", isChecked).apply()
        }

        // Video Quality - Click to change
        videoQuality.setOnClickListener {
            val options = arrayOf("Low", "Standard", "Higher")
            AlertDialog.Builder(this)
                .setTitle("Select Video Quality")
                .setItems(options) { _, which ->
                    videoQuality.text = options[which]
                    sharedPreferences.edit().putString("VIDEO_QUALITY", options[which]).apply()
                }
                .show()
        }

        // Delete All Downloads
        deleteDownloads.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete All Downloads?")
                .setMessage("Are you sure you want to delete all downloaded movies?")
                .setPositiveButton("Delete") { _, _ ->
                    Toast.makeText(this, "All downloads deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Mode (Light/Dark) Toggle
        modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("DARK_MODE", true).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("DARK_MODE", false).apply()
            }
            recreate() // Restart activity to apply theme change
        }

        // Load previously selected values
        cellularDataUsage.text = sharedPreferences.getString("CELLULAR_DATA_USAGE", "Low")
        videoQuality.text = sharedPreferences.getString("VIDEO_QUALITY", "Higher")
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
}
