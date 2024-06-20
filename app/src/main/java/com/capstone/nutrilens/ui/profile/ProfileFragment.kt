package com.capstone.nutrilens.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.capstone.nutrilens.MainActivity
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.databinding.DialogKeluarBinding
import com.capstone.nutrilens.databinding.FragmentProfileBinding
import com.capstone.nutrilens.ui.changeprofile.ChangeProfileFragment
import com.capstone.nutrilens.ui.darkmode.DarkModeViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var darkModeViewModel: DarkModeViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var preferences: Preferences

    private var userEmail: String? = null
    private var userProfileUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    userEmail = user?.email
                    userProfileUrl = user?.profileurl
                    binding.profileUsernameText.text = username
                    binding.emailText.text = userEmail
                    Glide.with(this)
                        .load(userProfileUrl)
                        .into(binding.profileImage)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonChangeProfile.setOnClickListener {
            val bundle = Bundle().apply {
                putString("userEmail", userEmail)
                putString("userProfileUrl", userProfileUrl) // Anda perlu menggantinya dengan nilai profil URL yang valid dari hasil panggilan API
            }
            findNavController().navigate(R.id.action_profileFragment_to_changeProfileFragment, bundle)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}