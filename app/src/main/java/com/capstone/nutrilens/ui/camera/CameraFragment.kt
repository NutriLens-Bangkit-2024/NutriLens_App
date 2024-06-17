package com.capstone.nutrilens.ui.camera

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.data.util.Result
import com.capstone.nutrilens.data.util.createCustomTempFile
import com.capstone.nutrilens.data.util.reduceFileImage
import com.capstone.nutrilens.data.util.uriToFile
import com.capstone.nutrilens.databinding.FragmentCameraBinding
import com.capstone.nutrilens.databinding.FragmentRecipeBinding
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class CameraFragment : Fragment() {


    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog :Dialog

    private val scanningViewmodel by viewModels<ScanningViewModel> {
        ScanningViewModelFactory.getInstance(requireContext())
    }
    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        preferences = Preferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUri = arguments?.getParcelable<Uri>("image_Uri")
        binding.ivCapturedImage.setImageURI(imageUri)

        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        val token = preferences.getToken().toString()

//        binding.buttonGetNutriScore.setOnClickListener {
//            imageUri?.let{uri->
//                val imageFile = uriToFile(uri, requireContext())
//                Log.d("Image File", "showImage: ${imageFile.path}")
//
//                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//                val multipartBody = MultipartBody.Part.createFormData(
//                    "photo",
//                    imageFile.name,
//                    requestImageFile
//                )
//                scanningViewmodel.getScanningResponse(preferences.getToken().toString(),multipartBody)
//                scanningViewmodel.scanningResult.observe(viewLifecycleOwner){result->
//                    when(result){
//                        is NetworkResult.Loading-> showLoading(true)
//                        is NetworkResult.Success ->{
//                            showLoading(false)
//                            dialog.setContentView(R.layout.dialog_detail_nutrisi)
//                            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                            dialog.show()
//                            result.data?.let { data ->
//                                val et: EditText = dialog.findViewById(R.id.edt_beratTakaranSaji)
//                                et.setText(data.takaranSaji.toString())
//                            }
//                        }
//                        is NetworkResult.Error->{
//                            showLoading(false)
//                            Toast.makeText(requireContext(),"Failed to get data",Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }
//            } ?: Toast.makeText(requireContext(),"No image to be processed",Toast.LENGTH_LONG).show()
//        }
        binding.buttonGetNutriScore.setOnClickListener {
            imageUri?.let {uri->
                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")
                scanningViewmodel.getScanningResponse(token,imageFile).observe(viewLifecycleOwner){result->
                    when(result){
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
//                            Toast.makeText(requireContext(),result.data.takaranSaji.toString(),Toast.LENGTH_LONG).show()
//                            dialog.setContentView(R.layout.dialog_detail_nutrisi)
//                            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                            dialog.show()
//
//                            result.data.let {
//                                val et: EditText = dialog.findViewById(R.id.edt_beratTakaranSaji)
//                                et.setText(it.takaranSaji.toString())
//                            }
                            result.data.let {
//                                showDialogDetailNutrisi(it.takaranSaji)
                                showDialogDetailNutrisi(it.takaranSaji,it.energi,it.protein,it.lemak,it.karbohidrat,it.serat,it.natrium)
                            }
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(requireContext(),"Failed to get data",Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }

    }

    private fun showDialogDetailNutrisi(takaranSaji: Int, energi: Any, protein: Any, lemak: Any, karbohidrat: Any, serat: Any, natrium: Any) {
        dialog.setContentView(R.layout.dialog_detail_nutrisi)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val edtTakaranSaji: TextInputEditText = dialog.findViewById(R.id.edt_beratTakaranSaji)
        edtTakaranSaji.setText(takaranSaji.toString())
        val edtEnergi: TextInputEditText = dialog.findViewById(R.id.edt_beratEnergi)
        edtEnergi.setText(energi.toString())
        val edtProtein: TextInputEditText = dialog.findViewById(R.id.edt_beratProtein)
        edtProtein.setText(protein.toString())
        val edtLemak: TextInputEditText = dialog.findViewById(R.id.edt_beratLemak)
        edtLemak.setText(lemak.toString())
        val edtKarbohidrat: TextInputEditText = dialog.findViewById(R.id.edt_beratKarbohidrat)
        edtKarbohidrat.setText(karbohidrat.toString())
        val edtSerat: TextInputEditText = dialog.findViewById(R.id.edt_beratSerat)
        edtSerat.setText(serat.toString())
        val edtNatrium: TextInputEditText = dialog.findViewById(R.id.edt_beratNatrium)
        edtNatrium.setText(natrium.toString())
    }

    private fun showDialogDetailNutrisi(takaranSaji: Int) {
        dialog.setContentView(R.layout.dialog_detail_nutrisi)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val et: TextInputEditText = dialog.findViewById(R.id.edt_beratTakaranSaji)
        et.setText(takaranSaji.toString())
    }

    private fun showLoading(boolean: Boolean) {
        binding.processNutriScoreProgressBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    private fun showNutriscore() {
        dialog.setContentView(R.layout.dialog_nutriscore)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val btnYesshow : Button = dialog.findViewById(R.id.btn_mengerti)
        btnYesshow.setOnClickListener {
            dialog.setContentView(R.layout.dialog_ingin_mengonsumsi)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()


        }
    }

}