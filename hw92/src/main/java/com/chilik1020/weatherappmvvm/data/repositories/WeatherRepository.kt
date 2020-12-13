package com.chilik1020.weatherappmvvm.data.repositories

import com.chilik1020.weatherappmvvm.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCase

interface WeatherRepository {

    fun getCurrentWeather(
        location: String,
        listener: CheckCurrentWeatherForCityUseCase.OnFinished
    )

    fun getHourlyForecastWeather(
        lat: String,
        lon: String,
        listener: ForecastWeatherUseCase.OnFinished
    )

    fun close()
}