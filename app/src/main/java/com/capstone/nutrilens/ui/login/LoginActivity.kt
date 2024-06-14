package com.capstone.nutrilens.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.capstone.nutrilens.MainActivity
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.response.LoginRequest
import com.capstone.nutrilens.data.response.LoginResponse
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.databinding.ActivityLoginBinding
import com.capstone.nutrilens.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preferences = Preferences(this)

        binding.loginButton.setOnClickListener {
            val loginRequest = LoginRequest(
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
            viewModel.login(loginRequest)
        }

        viewModel.loginResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val login = response.data
                    if (login?.status == "success") {
                        login.token?.let { token ->
                            preferences.saveToken(token)
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, response.exception ?: "An error occurred", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = response.isLoading
                }
            }
        }

        binding.textToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}