package com.chilik1020.weatherappmvp.presentation.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chilik1020.weatherappmvp.databinding.FragmentCityBinding

class CityFragment : Fragment(), CityContract.View {

    private lateinit var binding: FragmentCityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun render(state: CityViewState) {
        TODO("Not yet implemented")
    }
}