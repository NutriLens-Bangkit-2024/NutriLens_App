package com.capstone.nutrilens.ui.camera.predict

import androidx.lifecycle.ViewModel

class PredictionViewModel (private val predictionRepository: PredictionRepository):ViewModel(){
    fun savePredictionResult (token:String, label: String, calories: Int) = predictionRepository.savePredictionResult(token,label,calories)
}