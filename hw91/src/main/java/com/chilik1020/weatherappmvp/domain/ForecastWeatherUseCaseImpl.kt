package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.repositories.WeatherRepository

class ForecastWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : ForecastWeatherUseCase {
    override fun getHourlyForecast(lat: String, lon: String) =
        weatherRepository.getHourlyForecastWeather(lat, lon)
}