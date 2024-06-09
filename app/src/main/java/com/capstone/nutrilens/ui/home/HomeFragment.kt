package com.capstone.nutrilens.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.nutrilens.R
import com.capstone.nutrilens.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}