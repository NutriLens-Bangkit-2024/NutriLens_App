package com.capstone.nutrilens.ui.camera.scanning

import androidx.lifecycle.ViewModel
import java.io.File

class ScanningViewModel (private val repository: ScanningRepository): ViewModel() {
    fun getScanningResponse(token: String, imageFile : File) = repository.getScanningResponse(token,imageFile)
}