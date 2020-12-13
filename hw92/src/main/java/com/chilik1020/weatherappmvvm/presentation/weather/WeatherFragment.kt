package com.chilik1020.weatherappmvvm.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chilik1020.weatherappmvvm.R
import com.chilik1020.weatherappmvvm.databinding.FragmentWeatherBinding
import com.chilik1020.weatherappmvvm.presentation.city.CityFragment
import com.chilik1020.weatherappmvvm.presentation.models.WeatherForecastUiModel
import com.chilik1020.weatherappmvvm.presentation.settings.SettingsFragment
import com.chilik1020.weatherappmvvm.utils.ICON_BASE_URL
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class WeatherFragment : Fragment(), WeatherContract.View {

    private lateinit var binding: FragmentWeatherBinding
    private val presenter: WeatherContract.Presenter by inject()

    private val adapterWeatherForecast = WeatherForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_weather_frag, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miSettings) {
            navigateToSettingsFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun render(state: WeatherForecastViewState) {
        when (state) {
            is WeatherForecastViewState.Loading -> {
                binding.pbWeatherLoading.visibility = View.VISIBLE
            }
            is WeatherForecastViewState.Loaded -> {
                binding.pbWeatherLoading.visibility = View.GONE
                binding.tvCityName.text = state.city.name
                setFields(state.data)
            }
            is WeatherForecastViewState.Error -> {
                binding.pbWeatherLoading.visibility = View.GONE
                Snackbar.make(binding.root, state.error.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarWeatherFrag)
        setHasOptionsMenu(true)

        binding.rvWeatherForecast.apply {
            adapter = adapterWeatherForecast
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.fabNavigateToCityFrag.setOnClickListener { navigateToCityFragment() }
    }

    private fun setFields(data: WeatherForecastUiModel) {
        with(binding) {
            val temp = data.current.temp + "°C"
            tvTempCurrent.text = temp
            tvWeatherDescription.text = data.current.weatherList[0].description
            Glide.with(root)
                .load(ICON_BASE_URL.format(data.current.weatherList[0].icon))
                .into(ivWeatherIcon)

            adapterWeatherForecast.setData(data.hourlyList)
        }
    }

    private fun navigateToSettingsFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SettingsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToCityFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CityFragment())
            .addToBackStack(null)
            .commit()
    }
}