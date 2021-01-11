package com.chilik1020.weatherappmvp.domain.models

import com.chilik1020.weatherappmvp.data.entities.WeatherAtTimeStamp

data class WeatherForecastDomainModel(
    val lat: String,
    val lon: String,
    val current: WeatherAtTimeStamp,
    val hourlyList: List<WeatherAtTimeStamp>
)