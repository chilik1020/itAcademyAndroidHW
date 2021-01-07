package com.chilik1020.weatherappmvp.domain.models

import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentTop

fun interface CityDomainFromWeatherCurrentMapper {
    fun map(weatherCurrent: WeatherCurrentTop): CityDomainModel
}