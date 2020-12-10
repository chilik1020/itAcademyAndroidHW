package com.chilik1020.weatherappmvp.presentation.weather

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
import com.chilik1020.weatherappmvp.R
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject
import com.chilik1020.weatherappmvp.databinding.FragmentWeatherBinding
import com.chilik1020.weatherappmvp.presentation.settings.SettingsFragment
import com.chilik1020.weatherappmvp.utils.ICON_BASE_URL
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
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_weather_frag, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miSettings) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SettingsFragment())
                .addToBackStack(null)
                .commit()
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
            }
            is WeatherForecastViewState.Loaded -> {
                setFields(state.data)
            }
            is WeatherForecastViewState.Error -> {
            }
        }
    }

    private fun initViews() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarWeatherFrag)
        setHasOptionsMenu(true)
        presenter.attachView(this)

        binding.rvWeatherForecast.apply {
            adapter = adapterWeatherForecast
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setFields(data: WeatherForecastTopObject) {
        with(binding) {
            tvCityName.text = "${data.lat} ${data.lon}"
            tvTempCurrent.text = data.current.temp
            tvWeatherDescription.text = data.current.weatherList[0].description
            Glide.with(root)
                .load(ICON_BASE_URL.format(data.current.weatherList[0].icon))
                .into(ivWeatherIcon)

            adapterWeatherForecast.setData(data.hourlyList)
        }
    }
}