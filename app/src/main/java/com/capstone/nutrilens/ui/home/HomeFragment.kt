package com.capstone.nutrilens.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.databinding.FragmentHomeBinding
import com.capstone.nutrilens.ui.news.NewsAdapter
import com.capstone.nutrilens.ui.news.NewsRepository
import com.capstone.nutrilens.ui.news.NewsViewModel
import com.capstone.nutrilens.ui.news.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        val repository = NewsRepository(ApiService.instanceRetrofit)
        newsViewModel = ViewModelProvider(this, ViewModelFactory(repository))[NewsViewModel::class.java]

        // Inisialisasi RecyclerView
        newsAdapter = NewsAdapter()
        binding.rvNewsHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newsAdapter
        }

        // Ambil data berita
        val authorization = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1cm46YXVkaWVuY2U6dGVzdCIsImlzcyI6InVybjppc3N1ZXI6dGVzdCIsInN1YiI6IlpkXzczQ2ktU2dpanVzSU0iLCJleHAiOjE3MTgzNDMxMDQsImlhdCI6MTcxODI1NjcwNH0.kEDTcIlg60nLEtxDg_42poFf5gKaeL90-5kd7LVWwrw"
        newsViewModel.getAllNews(authorization).observe(viewLifecycleOwner, Observer { response ->
            response?.data?.news?.let { newsList ->
                Log.d("HomeFragment", "News data received: $newsList")
                newsAdapter.setNewsList(newsList)
            } ?: Log.d("HomeFragment", "News data is null")
        })
    }

//    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(true)

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
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}