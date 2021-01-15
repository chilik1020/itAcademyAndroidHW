package com.chilik1020.weatherappmvvm.presentation.models

import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastDomainModel

fun interface WeatherForecastDomainToUiMapper {
    fun map(data: WeatherForecastDomainModel): WeatherForecastUiModel
}