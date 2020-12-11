package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.WeatherForecastDomainModel

interface ForecastWeatherUseCase {

    fun interface OnFinished {
        fun onResponse(data: Result<WeatherForecastDomainModel>)
    }

    fun getHourlyForecast(lat: String, lon: String, listener: ForecastWeatherUseCase.OnFinished)
}