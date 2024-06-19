package com.capstone.nutrilens.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.response.RecipesItem
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.databinding.FragmentHomeBinding
import com.capstone.nutrilens.ui.login.LoginActivity
import com.capstone.nutrilens.ui.news.NewsAdapter
import com.capstone.nutrilens.ui.news.NewsRepository
import com.capstone.nutrilens.ui.news.NewsViewModel
import com.capstone.nutrilens.ui.news.ViewModelFactory
import com.capstone.nutrilens.ui.progress.CaloriesRepository
import com.capstone.nutrilens.ui.progress.ProgressViewModel
import com.capstone.nutrilens.ui.progress.ProgressViewModelFactory
import com.capstone.nutrilens.ui.recipe.RecipeBesarAdapter
import com.capstone.nutrilens.ui.recipe.RecipeDetailActivity
import com.capstone.nutrilens.ui.recipe.RecipeViewModel
import com.capstone.nutrilens.ui.recipe.RecipeViewModelFactory
import kotlin.math.ceil

class HomeFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recipeAdapter: RecipeBesarAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var preferences: Preferences

    private val recipeViewModel by viewModels<RecipeViewModel> {
        RecipeViewModelFactory.getInstance(requireContext())
    }

    private lateinit var caloriesViewModel: ProgressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        preferences = Preferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        fun updateProgressBarColor(progressBar: ProgressBar, calorieValue: Int) {
//            val maxCalories = 2500
//            val midCalories = 1500
//            val drawable = when {
//                calorieValue <= midCalories -> R.drawable.progress_bar_low
//                calorieValue <= maxCalories -> R.drawable.progress_bar_mid
//                else -> R.drawable.progress_bar_high
//            }
//            progressBar.progressDrawable = context?.let { ContextCompat.getDrawable(it, drawable) }
//        }

        // Inisialisasi ViewModel
        val repository = NewsRepository(ApiService.instanceRetrofit)
        newsViewModel = ViewModelProvider(this, ViewModelFactory(repository))[NewsViewModel::class.java]

        caloriesViewModel = ViewModelProvider(this,ProgressViewModelFactory(CaloriesRepository(ApiService.instanceRetrofit)))[ProgressViewModel::class.java]
        caloriesViewModel.fetchCaloriesData("Bearer ${preferences.getToken().toString()}")

        caloriesViewModel.caloriesResult.observe(viewLifecycleOwner){result->
            when(result){
                is NetworkResult.Loading-> binding.calorieProgressBar.progress = 0
                is NetworkResult.Success->{
                    val caloriesHarian: List<Int> = result.data?.data?.dailyCalories?.values?.toList() ?: emptyList()
                    val latestValue = caloriesHarian.firstOrNull() ?: 0
                    val latest : Int =
                        if (latestValue>0){
                            ceil( latestValue*100/2500.0).toInt()
                        }else{
                            0
                        }
                    binding.calorieProgressBar.progress = latest
//                    updateProgressBarColor(binding.calorieProgressBar, latestValue)
                }
                is NetworkResult.Error-> {
                    Toast.makeText(requireContext(),"Gagal mendapatkan data progress harian",Toast.LENGTH_LONG).show()
                }
            }
        }

        newsAdapter = NewsAdapter()
        binding.rvNewsHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newsAdapter
        }

        newsViewModel.getAllNews("Bearer ${preferences.getToken().toString()}").observe(viewLifecycleOwner, Observer { response ->
            response?.data?.news?.let { newsList ->
                Log.d("HomeFragment", "News data received: $newsList")
                newsAdapter.setNewsList(newsList)
            } ?: Log.d("HomeFragment", "News data is null")
        })

        recipeViewModel.getRecipes(preferences.getToken().toString()).observe(viewLifecycleOwner) {
            recipeViewModel.recipeData().observe(viewLifecycleOwner) { recipe ->
                if (recipe != null) {
                    setRecipeData((recipe))
                } else {
                    Toast.makeText(requireContext(), "Gagal mendapatkan data", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setRecipeData(recipe: List<RecipesItem>) {
        val adapter = RecipeBesarAdapter()
        adapter.submitList(recipe)
        binding.rvRecipeHome.adapter = adapter
        adapter.setOnItemClickCallback(object: RecipeBesarAdapter.OnItemClickCallback{
            override fun onItemClicked(recipe: RecipesItem, options: ActivityOptionsCompat) {
                val recipeDetailIntent = Intent(requireContext(), RecipeDetailActivity::class.java)
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_PHOTO,recipe.image)
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_NAME,recipe.name)
                recipeDetailIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_CALORIES,recipe.calories)
                recipeDetailIntent.putStringArrayListExtra(RecipeDetailActivity.EXTRA_RECIPE_INGREDIENTS,
                    ArrayList(recipe.ingredient)
                )
                recipeDetailIntent.putStringArrayListExtra(RecipeDetailActivity.EXTRA_RECIPE_DIRECTIONS,ArrayList(recipe.directions))
                startActivity(recipeDetailIntent,options.toBundle())
            }
        })

    }
}

//    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(true)

//        binding.btnInginMengonsumsi.setOnClickListener {
//            dialog.setContentView(R.layout.dialog_ingin_mengonsumsi)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
//
//
//            val btnYes : Button = dialog.findViewById(R.id.btn_ingin_makan_yakin)
//            btnYes.setOnClickListener {
//                Toast.makeText(requireContext(),"THE YES BUTTON WORKS NOW",Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
//            val btnNo : Button = dialog.findViewById(R.id.btn_ingin_makan_kembali)
//            btnNo.setOnClickListener {
//                Toast.makeText(requireContext(),"THE NO BUTTON WORKS NOW",Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
//        }
//
//        binding.btnDialogNutriscore.setOnClickListener {
//            dialog.setContentView(R.layout.dialog_nutriscore)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
//
//            val btnMengerti : Button = dialog.findViewById(R.id.btn_mengerti)
//            btnMengerti.setOnClickListener {
//                Toast.makeText(requireContext(),"THE YES BUTTON WORKS NOW",Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
//        }
//
//        binding.btnDialogKeluar.setOnClickListener {
//            dialog.setContentView(R.layout.dialog_keluar)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
//
//
//            val btnYes : Button = dialog.findViewById(R.id.btn_keluar_yakin)
//            btnYes.setOnClickListener {
//                Toast.makeText(requireContext(),"THE YES BUTTON WORKS NOW",Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
//            val btnNo : Button = dialog.findViewById(R.id.btn_keluar_kembali)
//            btnNo.setOnClickListener {
//                Toast.makeText(requireContext(),"THE NO BUTTON WORKS NOW",Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
//        }
//
//        binding.btnDialogSukses.setOnClickListener {
//            dialog.setContentView(R.layout.dialog_success_password)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
//
//            val btnMengerti: Button = dialog.findViewById(R.id.btn_password_mengerti)
//            btnMengerti.setOnClickListener {
//                Toast.makeText(requireContext(),"THE YES BUTTON WORKS NOW",Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
//        }
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
