package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastDomainModel
import io.reactivex.Single

interface ForecastWeatherUseCase {
    fun getHourlyForecast(lat: String, lon: String): Single<WeatherForecastDomainModel>
}