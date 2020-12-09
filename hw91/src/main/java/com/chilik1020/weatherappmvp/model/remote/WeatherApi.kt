package com.chilik1020.weatherappmvp.model.remote

import com.chilik1020.weatherappmvp.model.entities.Units
import com.chilik1020.weatherappmvp.model.entities.WeatherFore
import com.chilik1020.weatherappmvp.model.entities.WeatherForecastTopObject
import com.chilik1020.weatherappmvp.utils.API_KEY
import io.reactivex.Single

interface WeatherApi {

    fun getCurrentWeather(
        location: String = "Minsk",
        apiKey: String = API_KEY,
        units: String = Units.METRIC.value
    ): Single<WeatherFore>

    fun getHourlyForecastWeather(
        lat: String = "53.9",
        lon: String = "27.57",
        apiKey: String = API_KEY,
        units: String = Units.METRIC.value
    ): Single<WeatherForecastTopObject>
}