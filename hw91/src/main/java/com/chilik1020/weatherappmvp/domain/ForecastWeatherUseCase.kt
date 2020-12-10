package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject

interface ForecastWeatherUseCase {

    interface OnFinished {
        fun onResponse(data: Result<WeatherForecastTopObject>)
    }

    fun getHourlyForecast(lat: String, lon: String, listener: ForecastWeatherUseCase.OnFinished)
}