package com.capstone.nutrilens.ui.camera.model

import androidx.lifecycle.ViewModel

class ModelViewModel (private val modelRepository: ModelRepository):ViewModel() {
    fun getModelResponse (modelRequest : ModelRequest) = modelRepository.getModelResponse(modelRequest)
}