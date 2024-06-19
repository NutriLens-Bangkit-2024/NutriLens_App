package com.capstone.nutrilens.ui.camera

import android.app.Dialog
import android.content.Intent
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
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.capstone.nutrilens.MainActivity
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.data.util.Result
import com.capstone.nutrilens.data.util.reduceFileImage
import com.capstone.nutrilens.data.util.uriToFile
import com.capstone.nutrilens.databinding.FragmentCameraBinding
import com.capstone.nutrilens.ui.camera.model.ModelRequest
import com.capstone.nutrilens.ui.camera.model.ModelViewModel
import com.capstone.nutrilens.ui.camera.model.ModelViewModelFactory
import com.capstone.nutrilens.ui.camera.predict.PredictionRequest
import com.capstone.nutrilens.ui.camera.predict.PredictionViewModel
import com.capstone.nutrilens.ui.camera.predict.PredictionViewModelFactory
import com.capstone.nutrilens.ui.camera.scanning.ScanningViewModel
import com.capstone.nutrilens.ui.camera.scanning.ScanningViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class CameraFragment : Fragment() {


    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: Dialog

    private val scanningViewmodel by viewModels<ScanningViewModel> {
        ScanningViewModelFactory.getInstance(requireContext())
    }

    private val modelViewModel by viewModels<ModelViewModel> {
        ModelViewModelFactory.getInstance(requireContext())
    }

    private val predictionViewModel by viewModels<PredictionViewModel> {
        PredictionViewModelFactory.getInstance(requireContext())
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
            imageUri?.let { uri ->
                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")
                scanningViewmodel.getScanningResponse(token, imageFile)
                    .observe(viewLifecycleOwner) { result ->
                        when (result) {
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
                                    showDialogDetailNutrisi(
                                        it.takaranSaji,
                                        it.energi,
                                        it.protein,
                                        it.lemak,
                                        it.karbohidrat,
                                        it.serat,
                                        it.natrium
                                    )
                                }
                            }

                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    requireContext(),
                                    "Failed to get data",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }
            }
        }

    }

    private fun showDialogDetailNutrisi(
        takaranSaji: Int,
        energi: Any,
        protein: Any,
        lemak: Any,
        karbohidrat: Any,
        serat: Any,
        natrium: Any
    ) {
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

        val prgrsBar : ProgressBar = dialog.findViewById(R.id.detailNutrisi_progress_bar)


        val btnBenar: Button = dialog.findViewById(R.id.btn_benar)
        btnBenar.setOnClickListener {
            val valueTakaransaji = edtTakaranSaji.text.toString().toInt()
            val valueEnergi = edtEnergi.text.toString().toFloat()
            val valueProtein = edtProtein.text.toString().toFloat()
            val valueLemak = edtLemak.text.toString().toFloat()
            val valueKarbohidrat = edtKarbohidrat.text.toString().toFloat()
            val valueSerat = edtSerat.text.toString().toFloat()
            val valueNatrium = edtNatrium.text.toString().toFloat()

            val modelRequest = ModelRequest(
                takaran_saji = valueTakaransaji,
                energi = valueEnergi,
                protein = valueProtein,
                lemak = valueLemak,
                karbohidrat = valueKarbohidrat,
                serat = valueSerat,
                natrium = valueNatrium
            )
            modelViewModel.getModelResponse(modelRequest).observe(viewLifecycleOwner) { result ->
                when (result){
                    is Result.Loading-> prgrsBar.visibility = View.VISIBLE
                    is Result.Success-> {
                        prgrsBar.visibility = View.GONE
                        Log.d("ModelResponse", "Success: ${result.data}")
                        result.data.let {
                            showNutriScore(it.score,it.calPer100g)
//                            Toast.makeText(requireContext(),"${it.score.toString()}, ${it.calPer100g.toString()}",Toast.LENGTH_LONG).show()
                        }
                    }
                    is Result.Error->{
                        prgrsBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Failed to get data",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showNutriScore(score: String, calPer100g: Int) {
        dialog.setContentView(R.layout.dialog_nutriscore)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val ivNutriScore :ImageView = dialog.findViewById(R.id.iv_nutriscore)
        when(score){
            "A"-> ivNutriScore.setImageResource(R.drawable.label_a)
            "B"-> ivNutriScore.setImageResource(R.drawable.label_b)
            "C"-> ivNutriScore.setImageResource(R.drawable.label_c)
            "D"-> ivNutriScore.setImageResource(R.drawable.label_d)
            "E"-> ivNutriScore.setImageResource(R.drawable.label_e)
            else ->ivNutriScore.setImageResource(R.drawable.baseline_question_mark_24)
        }

        val tvDialogKalori : TextView = dialog.findViewById(R.id.tv_dialog_kalori)
        tvDialogKalori.text = "Makanan kemasan ini mengandung $calPer100g kalori"

        val btnMengerti : Button = dialog.findViewById(R.id.btn_mengerti)
        btnMengerti.setOnClickListener{
            dialog.setContentView(R.layout.dialog_ingin_mengonsumsi)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val progressBar : ProgressBar = dialog.findViewById(R.id.dialogKonsumsi_progress_bar)

            val btnKembali : Button = dialog.findViewById(R.id.btn_ingin_makan_kembali)
            btnKembali.setOnClickListener{
                findNavController().navigate(R.id.action_CameraFragment_to_homeFragment)
                dialog.dismiss()
            }
            val btnYakin : Button = dialog.findViewById(R.id.btn_ingin_makan_yakin)
            btnYakin.setOnClickListener{
                val request = PredictionRequest(score, calPer100g)
                predictionViewModel.savePredictionResult(preferences.getToken().toString(), score, calPer100g.toInt() ).observe(viewLifecycleOwner){result->
                    when(result){
                        is Result.Loading-> progressBar.visibility = View.VISIBLE
                        is Result.Success->{
                            progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(),result.data.toString(),Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_CameraFragment_to_progressFragment)
                            dialog.dismiss()
                        }
                        is Result.Error->{
                            progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(),result.error.toString(),Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_CameraFragment_to_homeFragment)
                            dialog.dismiss()
                        }
                    }
                }
            }

        }
    }

//    private fun showNutriscore() {
//
//
//        val btnYesshow: Button = dialog.findViewById(R.id.btn_mengerti)
//        btnYesshow.setOnClickListener {
//            dialog.setContentView(R.layout.dialog_ingin_mengonsumsi)
//            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
//
//
//        }
//    }

    private fun showLoading(boolean: Boolean) {
        binding.processNutriScoreProgressBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }

}