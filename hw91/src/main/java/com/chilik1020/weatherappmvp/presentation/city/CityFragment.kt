package com.chilik1020.weatherappmvp.presentation.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.weatherappmvp.R
import com.chilik1020.weatherappmvp.databinding.FragmentCityBinding
import com.chilik1020.weatherappmvp.presentation.addcity.AddCityDialogFragment
import org.koin.android.ext.android.inject

class CityFragment : Fragment(), CityContract.View {

    private lateinit var binding: FragmentCityBinding
    private val presenter: CityContract.Presenter by inject()
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
        presenter.attachView(this)
        binding.rvCityList.apply {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        binding.fabAddCityDialog.setOnClickListener { navigateToAddCityDialogFragment() }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCities()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
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

    private fun navigateToAddCityDialogFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AddCityDialogFragment())
            .addToBackStack(null)
            .commit()
    }
}