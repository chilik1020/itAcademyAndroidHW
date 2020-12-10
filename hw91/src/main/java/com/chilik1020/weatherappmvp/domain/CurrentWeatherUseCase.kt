package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentTopObject

interface CurrentWeatherUseCase {

    fun interface OnFinished {
        fun onResponse(result: Result<WeatherCurrentTopObject>)
    }

    fun getCurrentWeather(location: String, listener: CurrentWeatherUseCase.OnFinished)
}