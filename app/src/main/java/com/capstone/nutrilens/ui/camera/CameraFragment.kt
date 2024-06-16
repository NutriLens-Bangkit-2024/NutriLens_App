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
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.util.createCustomTempFile
import com.capstone.nutrilens.databinding.FragmentCameraBinding
import com.capstone.nutrilens.databinding.FragmentRecipeBinding

class CameraFragment : Fragment() {


    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog :Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imageUri = arguments?.getParcelable<Uri>("image_Uri")
        binding.ivCapturedImage.setImageURI(imageUri)

        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        binding.buttonGetNutriScore.setOnClickListener {
            dialog.setContentView(R.layout.dialog_detail_nutrisi)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()


            val btnYes : Button = dialog.findViewById(R.id.btn_benar)
            btnYes.setOnClickListener {
                showNutriscore()
            }
//            dialog.dismiss()
//            val btnNo : Button = dialog.findViewById(R.id.btn_ingin_makan_kembali)
//            btnNo.setOnClickListener {
//                Toast.makeText(requireContext(),"THE NO BUTTON WORKS NOW",Toast.LENGTH_LONG).show()
//                dialog.dismiss()
//            }
        }

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