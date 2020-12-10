package com.chilik1020.weatherappmvp.data.repositories

import com.chilik1020.weatherappmvp.domain.CurrentWeatherUseCase
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase

interface WeatherRepository {

    fun getCurrentWeather(
        location: String,
        listener: CurrentWeatherUseCase.OnFinished
    )

    fun getHourlyForecastWeather(
        lat: String,
        lon: String,
        listener: ForecastWeatherUseCase.OnFinished
    )
}