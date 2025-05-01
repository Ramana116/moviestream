package com.sdp.movietime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var edtreEnterPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtName = findViewById(R.id.usernameEditText)
        edtemail = findViewById(R.id.emailEditText)
        edtpassword = findViewById(R.id.passwordEditText)
        edtreEnterPassword = findViewById(R.id.rePasswordEditText)
        btnRegister = findViewById(R.id.registerButton)

        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = edtemail.text.toString().trim()
        val username = edtName.text.toString().trim()
        val password = edtpassword.text.toString().trim()
        val reEnterPassword = edtreEnterPassword.text.toString().trim()

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(email, username, password, reEnterPassword)

        RetrofitClient.instance.registerUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("profile_name", username)
                    editor.apply()
                    Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
