package com.capstone.nutrilens.ui.changeprofile

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.di.Injection.provideChangeProfileRepository
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.databinding.DialogSuccessPasswordBinding
import com.capstone.nutrilens.databinding.FragmentChangeProfileBinding
import at.favre.lib.crypto.bcrypt.BCrypt
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.ui.profile.ProfileFragment

class ChangeProfileFragment : Fragment() {
    private lateinit var changeProfileViewModel: ChangeProfileViewModel
    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val changeProfileRepository = provideChangeProfileRepository()
        val changeProfileFactory = ChangeProfileFactory.getInstance(changeProfileRepository)
        changeProfileViewModel = ViewModelProvider(this, changeProfileFactory)[ChangeProfileViewModel::class.java]

        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", null)
        val token = sharedPreferences.getString("TOKEN", null)

        binding.btnChangeProfile.setOnClickListener {
            val newName = binding.edtChangeUsername.text.toString()
            val newPassword = binding.edtChangePassword.text.toString()
            val confirmNewPassword = binding.edtConfirmPassword.text.toString()

            if (newPassword != confirmNewPassword) {
                Toast.makeText(requireContext(), "New passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (id != null && token != null) {
                val editUserRequest = EditUserRequest(
                    email = null,
                    password = newPassword,
                    name = newName,
                    profileUrl = null
                )
                Log.d("ChangeProfileFragment", "Sending EditUserRequest: $editUserRequest")
                changeProfileViewModel.editUser("Bearer $token", id, editUserRequest)
            } else {
                Toast.makeText(requireContext(), "User ID or Token not found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnChangeProfileKembali.setOnClickListener {
            findNavController().popBackStack()
        }

        changeProfileViewModel.editUserResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showSuccessDialog()
                    updateUserProfile()
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), result.exception, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun updateUserProfile() {
        val profileFragment = parentFragmentManager.findFragmentByTag("ProfileFragment")
        if (profileFragment is ProfileFragment) {
            profileFragment.updateUserProfile()
        }
    }

    private fun showSuccessDialog() {
        val dialogBinding = DialogSuccessPasswordBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialogBinding.btnPasswordMengerti.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}