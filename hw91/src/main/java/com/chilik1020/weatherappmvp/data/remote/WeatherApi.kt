package com.chilik1020.weatherappmvp.data.remote

import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentTopObject
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject
import io.reactivex.Single

interface WeatherApi {

    fun getCurrentWeather(
        location: String,
        apiKey: String,
        units: String
    ): Single<WeatherCurrentTopObject>

    fun getHourlyForecastWeather(
        lat: String,
        lon: String,
        apiKey: String,
        units: String
    ): Single<WeatherForecastTopObject>
}