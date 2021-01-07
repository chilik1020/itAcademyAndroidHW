package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.WeatherForecastDomainModel
import io.reactivex.Single

interface ForecastWeatherUseCase {
    fun getHourlyForecast(lat: String, lon: String): Single<WeatherForecastDomainModel>
}