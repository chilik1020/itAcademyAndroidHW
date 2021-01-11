package com.chilik1020.weatherappmvp.presentation.weather

import com.chilik1020.weatherappmvp.presentation.models.CityUiModel
import com.chilik1020.weatherappmvp.presentation.models.WeatherForecastUiModel

sealed class WeatherForecastViewState {
    object Loading : WeatherForecastViewState()
    class Loaded(val data: WeatherForecastUiModel, val city: CityUiModel) :
        WeatherForecastViewState()

    class Error(val error: String) : WeatherForecastViewState()
}