package com.chilik1020.weatherappmvvm.presentation.weather

import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel
import com.chilik1020.weatherappmvvm.presentation.models.WeatherForecastUiModel

sealed class WeatherForecastViewState {
    object Loading : WeatherForecastViewState()
    class Loaded(val data: WeatherForecastUiModel, val city: CityUiModel) :
        WeatherForecastViewState()

    class Error(val error: String) : WeatherForecastViewState()
}