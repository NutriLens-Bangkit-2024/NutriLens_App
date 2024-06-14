package com.capstone.nutrilens.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.nutrilens.data.response.RecipesItem
import com.capstone.nutrilens.databinding.FragmentHomeBinding
import com.capstone.nutrilens.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val recipeViewModel by viewModels<RecipeViewModel> {
        RecipeViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1cm46YXVkaWVuY2U6dGVzdCIsImlzcyI6InVybjppc3N1ZXI6dGVzdCIsInN1YiI6Ik84TmV0ZDVhbGRSaXRyTjQiLCJleHAiOjE3MTgzNzIyNjIsImlhdCI6MTcxODI4NTg2Mn0.feP0QqKY31IgZiLeuc0K7jSrkUmegOgJA8n21nLFf38"
        recipeViewModel.getRecipes(token).observe(viewLifecycleOwner){
            recipeViewModel.recipeData().observe(viewLifecycleOwner){recipe->
                if(recipe!=null){
                    setRecipeData(recipe)
                }else{
                    Toast.makeText(requireContext(),"Gagal mendapatkan data",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun setRecipeData(recipe: List<RecipesItem>) {
        val adapter = RecipeBesarAdapter()
        adapter.submitList(recipe)
        binding.rvRecipesBesar.adapter = adapter
        adapter.setOnItemClickCallback(object: RecipeBesarAdapter.OnItemClickCallback{
            override fun onItemClicked(recipe: RecipesItem, options: ActivityOptionsCompat) {
                val recipeDetailIntent = Intent(requireContext(),RecipeDetailActivity::class.java)
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_PHOTO,"https://www.allrecipes.com/thmb/9aWCdbfttLcsW2dFQWwVQBGJM3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AR-236973-CreamyAlfredoSauce-0238-4x3-1-01e7091f47ae452d991abe32cbed5921.jpg")
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_NAME,recipe.name)
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_CALORIES,recipe.calories)
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_INGREDIENTS,recipe.ingredient)
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_DIRECTIONS,recipe.directions)
                startActivity(recipeDetailIntent,options.toBundle())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}