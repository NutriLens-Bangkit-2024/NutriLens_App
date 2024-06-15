package com.capstone.nutrilens.ui.progress

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutrilens.data.api.ApiService
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.databinding.FragmentProgressBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.Locale

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProgressViewModel

    private lateinit var barChart: BarChart
    private lateinit var barData: BarData
    private lateinit var barDataSet: BarDataSet
    private lateinit var barEntriesList: ArrayList<BarEntry>
    private val labels = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = CaloriesRepository(ApiService.instanceRetrofit)
        val factory = ProgressViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ProgressViewModel::class.java]

        barChart = binding.barChart

        viewModel.caloriesResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    val weeklyCalories = result.data?.data?.weeklyCalories
                    if (weeklyCalories != null) {
                        updateBarChart(weeklyCalories)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    // Handle loading state if needed
                }
            }
        }

        fetchCaloriesData()
        updateDateRange()  // Call updateDateRange here
    }

    private fun fetchCaloriesData() {
        val authorization = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1cm46YXVkaWVuY2U6dGVzdCIsImlzcyI6InVybjppc3N1ZXI6dGVzdCIsInN1YiI6IlVZWnA3ZS1ZRHZFd0pXMHAiLCJpYXQiOjE3MTgzNzg1NzR9.5kUX07vwT7xNQLTAIDimRAb6UGIDXiyczHjbg5Gz4bQ"
        viewModel.fetchCaloriesData(authorization)
    }

    private fun updateBarChart(weeklyCalories: Map<String, Int>) {
        barEntriesList = ArrayList()
        labels.forEachIndexed { index, day ->
            val calories = weeklyCalories[day] ?: 0
            barEntriesList.add(BarEntry(index.toFloat(), calories.toFloat()))
        }

        barDataSet = BarDataSet(barEntriesList, "Hari")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        barDataSet.valueTextSize = 20f
        barChart.description.isEnabled = false

        val xAxis: XAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = labels.size

        val legend: Legend = barChart.legend
        legend.isEnabled = true

        barChart.invalidate() // refresh
    }

    private fun updateDateRange() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val startDate = calendar.time

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val endDate = calendar.time

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateRange = "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
        binding.tvDate.text = dateRange
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}