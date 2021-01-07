package com.chilik1020.weatherappmvp.domain.models

import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTop

fun interface WeatherForecastToDomainMapper {
    fun map(data: WeatherForecastTop): WeatherForecastDomainModel
}