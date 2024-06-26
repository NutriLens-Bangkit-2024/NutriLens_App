package com.capstone.nutrilens.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.capstone.nutrilens.MainActivity
import com.capstone.nutrilens.data.response.LoginRequest
import com.capstone.nutrilens.data.session.UserModel
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.databinding.ActivityLoginBinding
import com.capstone.nutrilens.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<LoginViewModel>()

    private val userSessionViewModel by viewModels<UserSessionViewModel> {
        UserSessionViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preferences = Preferences(this)

        userSessionViewModel.getSession().observe(this){user->
            if (user.isLogin){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                val loginRequest = LoginRequest(
                    email = email,
                    password = password
                )
                viewModel.login(loginRequest)
            }
        }

        viewModel.loginResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val login = response.data
                    if (login?.status == "success") {
                        login.token?.let { token ->
                            preferences.saveToken(token)
                            login.id?.let { id ->
                                preferences.saveUserId(id)
                                userSessionViewModel.saveSession(UserModel(token,id))
                            }
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, "User not found, check your email and password", Toast.LENGTH_SHORT).show()
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