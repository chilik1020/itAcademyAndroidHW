package com.chilik1020.weatherappmvp.data.repositories

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import com.chilik1020.weatherappmvp.domain.models.WeatherForecastDomainModel
import io.reactivex.Single

interface WeatherRepository {

    fun getCityIfCurrentWeatherPresented(
        location: String,
    ): Single<CityDomainModel>

    fun getHourlyForecastWeather(
        lat: String,
        lon: String,
    ): Single<WeatherForecastDomainModel>
}