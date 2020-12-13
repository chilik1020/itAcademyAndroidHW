package com.chilik1020.weatherappmvvm.domain.models

import com.chilik1020.weatherappmvvm.data.entities.WeatherForecastTopObject

fun interface WeatherForecastToDomainMapper {
    fun map(data: WeatherForecastTopObject): WeatherForecastDomainModel
}