package com.sdp.movietime

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var oldUsernameValueTextView: TextView
    private lateinit var profileNameEditText: EditText
    private lateinit var saveButton: MaterialButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var updateProfileToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        // Initialize UI components
        oldUsernameValueTextView = findViewById(R.id.oldUsernameValueTextView)
        profileNameEditText = findViewById(R.id.profileName)
        saveButton = findViewById(R.id.saveButton)
        updateProfileToolbar = findViewById(R.id.updateProfileToolbar)

        // Set up Toolbar with Back Button
        setSupportActionBar(updateProfileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        // Load the old username and set it
        val oldUsername = sharedPreferences.getString("profile_name", "No username")
        oldUsernameValueTextView.text = oldUsername

        // Save Button Click Listener
        saveButton.setOnClickListener {
            val newUsername = profileNameEditText.text.toString().trim()
            if (newUsername.isNotEmpty()) {
                updateUsername(newUsername)
            } else {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUsername(newUsername: String) {
        if (newUsername.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBody = mapOf("newUsername" to newUsername)

        RetrofitClient.instance.updateUsername(requestBody).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // ✅ Update SharedPreferences with the new username
                    sharedPreferences.edit().putString("profile_name", newUsername).apply()

                    Toast.makeText(this@UpdateProfileActivity, "Username updated successfully", Toast.LENGTH_SHORT).show()

                    // ✅ Notify ProfileActivity to update the TextView
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@UpdateProfileActivity, "Update failed: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UpdateProfileActivity, "Network Error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Handle Back Button click in Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
