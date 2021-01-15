package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.repositories.WeatherRepository

class CheckCurrentWeatherForCityUseCaseImpl(private val weatherRepository: WeatherRepository) :
    CheckCurrentWeatherForCityUseCase {
    override fun getCityIfCurrentWeatherPresented(location: String) =
        weatherRepository.getCityIfCurrentWeatherPresented(location)
}