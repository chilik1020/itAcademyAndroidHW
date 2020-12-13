package com.chilik1020.weatherappmvvm.presentation.city

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.weatherappmvvm.databinding.FragmentCityBinding
import com.chilik1020.weatherappmvvm.presentation.addcity.AddCityDialogFragment
import com.chilik1020.weatherappmvvm.utils.LOG_TAG
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class CityFragment : Fragment() {

    private lateinit var binding: FragmentCityBinding
    private val viewModel: CityViewModel by inject()

    private val onCityItemClickListener = OnCityItemClickListener {
        Log.d(LOG_TAG, it.toString())
        viewModel.setCityAsActive(it)
    }
    private val cityAdapter = CityAdapter(onCityItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCityList.apply {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        binding.fabAddCityDialog.setOnClickListener { navigateToAddCityDialogFragment() }

        viewModel.viewState.observe(viewLifecycleOwner) { render(it) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCities()
    }

    private fun render(state: CityViewState) {
        when (state) {
            is CityViewState.Loading -> {
                binding.pbCitiesLoading.visibility = View.VISIBLE
            }
            is CityViewState.Loaded -> {
                binding.pbCitiesLoading.visibility = View.GONE
                cityAdapter.setData(state.data)
            }
            is CityViewState.Error -> {
                binding.pbCitiesLoading.visibility = View.GONE
                Snackbar.make(binding.root, state.error, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToAddCityDialogFragment() {
        val dialogFragment = AddCityDialogFragment()
        dialogFragment.show(requireActivity().supportFragmentManager, null)
    }
}