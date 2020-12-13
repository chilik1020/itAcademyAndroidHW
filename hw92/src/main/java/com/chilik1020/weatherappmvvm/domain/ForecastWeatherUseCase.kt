package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastDomainModel

interface ForecastWeatherUseCase {

    fun interface OnFinished {
        fun onResponse(data: Result<WeatherForecastDomainModel>)
    }

    fun getHourlyForecast(lat: String, lon: String, listener: OnFinished)
}