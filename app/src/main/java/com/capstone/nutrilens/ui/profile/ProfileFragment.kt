package com.capstone.nutrilens.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.capstone.nutrilens.MainActivity
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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var darkModeViewModel: DarkModeViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var preferences: Preferences
    private lateinit var apiConfig: ApiConfig

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val switchTheme = binding.switchTheme
//        val pref = SettingPreference.getInstance(requireContext())
//        darkModeViewModel = ViewModelProvider(this, DarkModeFactory(pref))[DarkModeViewModel::class.java]
//
//        darkModeViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive ->
//            if (isDarkModeActive) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                switchTheme.isChecked = true
//                switchTheme.text = getString(R.string.dark_mode)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchTheme.isChecked = false
//                switchTheme.text = getString(R.string.light_mode)
//            }
//        }
//
//        switchTheme.setOnCheckedChangeListener { _, isChecked ->
//            darkModeViewModel.saveThemeSetting(isChecked)
//        }

        preferences = Preferences(requireContext())
        val repository = ProfileRepository(ApiService.instanceRetrofit)
        val factory = ProfileViewModelFactory(repository)
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        val token = preferences.getToken()
        val id = preferences.getUserId()

        if (token != null && id != null) {
            profileViewModel.fetchUser("Bearer $token", id)
        }
        profileViewModel.user.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // Tampilkan indikator loading jika diperlukan
                }
                is NetworkResult.Success -> {
                    val user = result.data?.data?.user
                    val username = user?.name
                    val email = user?.email
                    val profileUrl = user?.profileurl
                    binding.profileUsernameText.text = username
                    binding.emailText.text = email
                    Glide.with(this)
                        .load(profileUrl)
                        .into(binding.profileImage)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changeProfileFragment)
        }

        binding.buttonLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    fun updateUserProfile() {
        val token = preferences.getToken()
        val id = preferences.getUserId()

        if (token != null && id != null) {
            profileViewModel.fetchUser("Bearer $token", id)
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
            (activity as? MainActivity)?.logout()
//            logoutUser(dialog)
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