package com.capstone.nutrilens.ui.register

import androidx.lifecycle.ViewModel

class RegisterViewModel(private val registerRepository: RegisterRepository): ViewModel() {
    fun registerUser(name:String,email:String,password:String) = registerRepository.registerUser(email,password,name)
}