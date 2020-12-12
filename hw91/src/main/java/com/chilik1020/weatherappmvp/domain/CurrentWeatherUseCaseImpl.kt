package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.repositories.WeatherRepository

class CurrentWeatherUseCaseImpl(private val weatherRepository: WeatherRepository) :
    CurrentWeatherUseCase {
    override fun getCurrentWeather(location: String, listener: CurrentWeatherUseCase.OnFinished) {
        weatherRepository.getCurrentWeather(location, listener)
    }
}