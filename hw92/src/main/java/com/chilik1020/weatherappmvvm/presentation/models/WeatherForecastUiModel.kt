package com.chilik1020.weatherappmvvm.presentation.models

import com.chilik1020.weatherappmvvm.data.entities.WeatherAtTimeStamp

data class WeatherForecastUiModel(
    val lat: String,
    val lon: String,
    val current: WeatherAtTimeStamp,
    val hourlyList: List<WeatherAtTimeStamp>
)
