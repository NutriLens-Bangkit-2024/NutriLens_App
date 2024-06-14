package com.capstone.nutrilens.ui.recipe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.capstone.nutrilens.R
import com.capstone.nutrilens.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTitle.text = intent.getStringExtra(EXTRA_RECIPE_NAME)
        binding.tvCalories.text = intent.getStringExtra(EXTRA_RECIPE_CALORIES)
        binding.tvDescriptionBahan.text = intent.getStringExtra(EXTRA_RECIPE_INGREDIENTS)
        binding.tvDescriptionTahapan.text = intent.getStringExtra(EXTRA_RECIPE_DIRECTIONS)
        Glide.with(this@RecipeDetailActivity)
            .load(intent.getStringExtra(EXTRA_RECIPE_PHOTO))
            .centerCrop()
            .into(binding.ivPicture)

    }
    companion object{
        const val EXTRA_RECIPE_PHOTO = "extra_recipe_photo"
        const val EXTRA_RECIPE_NAME="extra_recipe_name"
        const val EXTRA_RECIPE_CALORIES="extra_recipe_CALORIES"
        const val EXTRA_RECIPE_INGREDIENTS = "extra_recipe_ingredients"
        const val EXTRA_RECIPE_DIRECTIONS = "extra_recipe_directions"

    }
}