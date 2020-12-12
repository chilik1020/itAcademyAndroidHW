package com.chilik1020.weatherappmvp.presentation.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.weatherappmvp.databinding.FragmentCityBinding

class CityFragment : Fragment(), CityContract.View {

    private lateinit var binding: FragmentCityBinding
    private val cityAdapter = CityAdapter()

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
    }

    override fun render(state: CityViewState) {
        when (state) {
            is CityViewState.Loading -> {
            }
            is CityViewState.Loaded -> {
                cityAdapter.setData(state.data)
            }
            is CityViewState.Error -> {

            }
        }
    }
}