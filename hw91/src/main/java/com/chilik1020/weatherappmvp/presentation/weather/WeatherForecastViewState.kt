package com.chilik1020.weatherappmvp.presentation.weather

import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject

sealed class WeatherForecastViewState {
    object Loading : WeatherForecastViewState()
    class Loaded(val data: WeatherForecastTopObject) : WeatherForecastViewState()
    class Error(val error: String) : WeatherForecastViewState()
}