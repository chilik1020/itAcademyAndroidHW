package com.chilik1020.weatherappmvp.presentation.models

import com.chilik1020.weatherappmvp.domain.models.WeatherForecastDomainModel

fun interface WeatherForecastDomainToUiMapper {
    fun map(data: WeatherForecastDomainModel): WeatherForecastUiModel
}