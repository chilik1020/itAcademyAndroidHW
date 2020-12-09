package com.chilik1020.weatherappmvp.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chilik1020.weatherappmvp.databinding.FragmentWeatherBinding
import com.chilik1020.weatherappmvp.model.entities.WeatherForecastTopObject
import com.chilik1020.weatherappmvp.model.remote.WeatherApiImpl
import com.chilik1020.weatherappmvp.utils.ICON_BASE_URL
import com.chilik1020.weatherappmvp.utils.LOG_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val weatherApi = WeatherApiImpl()
    private lateinit var subscribe: Disposable

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

        subscribe = weatherApi.getHourlyForecastWeather()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setFields(it) },
                { Log.d(LOG_TAG, it.message.toString()) }
            )
    }

    private fun initViews() {
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