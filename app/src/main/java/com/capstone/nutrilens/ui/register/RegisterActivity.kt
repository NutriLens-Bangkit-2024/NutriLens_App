package com.capstone.nutrilens.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.nutrilens.data.util.Result
import com.capstone.nutrilens.databinding.ActivityRegisterBinding
import com.capstone.nutrilens.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textToLogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.registerButtonRegister.setOnClickListener {
            if(binding.registerEmailField.text!=null && binding.registerNameField.text!=null && binding.registerPasswordField.text!=null){
                val name = binding.registerNameField.text.toString()
                val email = binding.registerEmailField.text.toString()
                val password = binding.registerPasswordField.text.toString()
                startRegistration(name,email,password)
            }else{
                Toast.makeText(this@RegisterActivity,"Pastikan data sudah lengkap sebelum menekan register!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startRegistration(name: String, email: String, password: String) {
        registerViewModel.registerUser(name,email,password).observe(this){result->
            if(result != null){
                when(result){
                    is Result.Loading -> binding.registerProgressBar.visibility = View.VISIBLE
                    is Result.Success ->{
                        binding.registerProgressBar.visibility = View.GONE
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle(result.data)
                            setMessage("Akun dengan $email sudah jadi nih. Yuk, login!")
                            setPositiveButton("Lanjut") { _, _ ->
                                startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is Result.Error-> {
                        binding.registerProgressBar.visibility = View.GONE
                        Toast.makeText(this,result.error?: "Unknown error occurred",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}