package com.chilik1020.weatherappmvp.domain.models

import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject

fun interface WeatherForecastToDomainMapper {
    fun map(data: WeatherForecastTopObject): WeatherForecastDomainModel
}