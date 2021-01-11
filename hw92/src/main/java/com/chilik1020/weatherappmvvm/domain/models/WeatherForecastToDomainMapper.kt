package com.chilik1020.weatherappmvvm.domain.models

import com.chilik1020.weatherappmvvm.data.entities.WeatherForecastTop

fun interface WeatherForecastToDomainMapper {
    fun map(data: WeatherForecastTop): WeatherForecastDomainModel
}