package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.data.repositories.WeatherRepository

class ForecastWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : ForecastWeatherUseCase {
    override fun getHourlyForecast(
        lat: String,
        lon: String,
        listener: ForecastWeatherUseCase.OnFinished
    ) {
        weatherRepository.getHourlyForecastWeather(lat, lon, listener)
    }
}