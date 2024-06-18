package com.capstone.nutrilens.ui.camera.model

data class ModelRequest(
    val takaran_saji: Int,
    val energi: Float,
    val protein: Float,
    val lemak: Float,
    val karbohidrat: Float,
    val serat: Float,
    val natrium: Float
) {
}