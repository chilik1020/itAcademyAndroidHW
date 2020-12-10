package com.chilik1020.weatherappmvp.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject
import com.chilik1020.weatherappmvp.databinding.FragmentWeatherBinding
import com.chilik1020.weatherappmvp.domain.WeatherContract
import com.chilik1020.weatherappmvp.domain.WeatherPresenter
import com.chilik1020.weatherappmvp.utils.ICON_BASE_URL

class WeatherFragment : Fragment(), WeatherContract.View {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var presenter: WeatherPresenter

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

    override fun setData(data: WeatherForecastTopObject) {
        setFields(data)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun initViews() {
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