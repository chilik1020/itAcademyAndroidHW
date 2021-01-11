package com.chilik1020.weatherappmvp.presentation.models

import com.chilik1020.weatherappmvp.data.entities.WeatherAtTimeStamp

data class WeatherForecastUiModel(
    val lat: String,
    val lon: String,
    val current: WeatherAtTimeStamp,
    val hourlyList: List<WeatherAtTimeStamp>
)
