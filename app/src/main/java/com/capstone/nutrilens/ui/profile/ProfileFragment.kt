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
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var darkModeViewModel: DarkModeViewModel
    private val profileViewModel by viewModels<ProfileViewModel> {
        ProfileViewModelFactory.getInstance(requireContext())
    }

    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        preferences = Preferences(requireContext())
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

        viewLifecycleOwner.lifecycleScope.launch {
            val token = preferences.getToken()
            if (token != null) {
                profileViewModel.getProfile("Bearer $token").observe(viewLifecycleOwner, Observer { userResult ->
                    when (userResult) {
                        is NetworkResult.Success -> {
                            val user = userResult.data
                            if (user != null) {
                                binding.profileUsernameText.text = user.name
                                binding.emailText.text = user.email
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
            preferences.clearSession()
            activity?.finishAffinity()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}