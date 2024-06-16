package com.capstone.nutrilens.ui.camera

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivCapturedImage.setImageURI(arguments?.getParcelable<Uri>("image_Uri"))

    }

}