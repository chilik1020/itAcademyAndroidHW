package com.chilik1020.weatherappmvvm.data.remote

import com.chilik1020.weatherappmvvm.data.entities.WeatherCurrentTop
import com.chilik1020.weatherappmvvm.data.entities.WeatherForecastTop
import io.reactivex.Single

interface WeatherApi {

    fun getCurrentWeather(
        location: String,
        units: String
    ): Single<WeatherCurrentTop>

    fun getHourlyForecastWeather(
        lat: String,
        lon: String,
        units: String
    ): Single<WeatherForecastTop>
}