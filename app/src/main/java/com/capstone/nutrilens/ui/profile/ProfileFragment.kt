package com.capstone.nutrilens.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.api.ApiConfig
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.databinding.DialogKeluarBinding
import com.capstone.nutrilens.databinding.FragmentProfileBinding
import com.capstone.nutrilens.ui.darkmode.DarkModeFactory
import com.capstone.nutrilens.ui.darkmode.DarkModeViewModel
import com.capstone.nutrilens.ui.darkmode.SettingPreference
import com.capstone.nutrilens.ui.progress.CaloriesRepository
import com.capstone.nutrilens.ui.progress.ProgressViewModel
import com.capstone.nutrilens.ui.progress.ProgressViewModelFactory
import com.capstone.nutrilens.ui.recipe.RecipeViewModel
import com.capstone.nutrilens.ui.recipe.RecipeViewModelFactory
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var darkModeViewModel: DarkModeViewModel
    private val profileViewModel by viewModels<ProfileViewModel> {
        ProfileViewModelFactory.getInstance(requireContext())
    }

    private lateinit var preferences: Preferences
    private lateinit var apiConfig: ApiConfig

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        preferences = Preferences(requireContext())
        apiConfig = ApiService.instanceRetrofit
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = binding.switchTheme
        val pref = SettingPreference.getInstance(requireContext())
        darkModeViewModel = ViewModelProvider(this, DarkModeFactory(pref))[DarkModeViewModel::class.java]

        darkModeViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
                switchTheme.text = getString(R.string.dark_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
                switchTheme.text = getString(R.string.light_mode)
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            darkModeViewModel.saveThemeSetting(isChecked)
        }

        val token = preferences.getToken()
        val id = preferences.getUserId()
        if (token != null && id != null) {
            profileViewModel.getProfile("Bearer $token", id).observe(viewLifecycleOwner, Observer { userResult ->
                when (userResult) {
                    is NetworkResult.Success -> {
                        val user = userResult.data
                        if (user != null) {
                            binding.profileUsernameText.text = user.name ?: ""
                            binding.emailText.text = user.email ?: ""
                        } else {
                            Toast.makeText(requireContext(), "User data is null", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), userResult.exception ?: "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                    }
                }
            })
        }

        binding.buttonChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changeProfileFragment)
        }

        binding.buttonLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val dialogBinding = DialogKeluarBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnKeluarKembali.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnKeluarYakin.setOnClickListener {
            logoutUser(dialog)
        }

        dialog.show()
    }

    private fun logoutUser(dialog: AlertDialog) {
        val token = preferences.getToken()
        if (token != null) {
            apiConfig.logout("Bearer $token").enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        preferences.clearSession()
                        dialog.dismiss()
                        activity?.finishAffinity()
                    } else {
                        Toast.makeText(requireContext(), "Logout failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "Token not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}