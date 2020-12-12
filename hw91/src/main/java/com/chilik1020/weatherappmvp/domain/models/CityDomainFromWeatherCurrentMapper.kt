package com.chilik1020.weatherappmvp.domain.models

import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentTopObject

fun interface CityDomainFromWeatherCurrentMapper {
    fun map(weatherCurrent: WeatherCurrentTopObject): CityDomainModel
}