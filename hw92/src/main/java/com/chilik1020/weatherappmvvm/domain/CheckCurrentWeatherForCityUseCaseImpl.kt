package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.data.repositories.WeatherRepository

class CheckCurrentWeatherForCityUseCaseImpl(private val weatherRepository: WeatherRepository) :
    CheckCurrentWeatherForCityUseCase {
    override fun getCurrentWeather(
        location: String,
        listener: CheckCurrentWeatherForCityUseCase.OnFinished
    ) {
        weatherRepository.getCurrentWeather(location, listener)
    }
}