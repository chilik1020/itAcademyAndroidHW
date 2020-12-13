package com.chilik1020.weatherappmvvm.domain.models

import com.chilik1020.weatherappmvvm.data.entities.WeatherCurrentTopObject

fun interface CityDomainFromWeatherCurrentMapper {
    fun map(weatherCurrent: WeatherCurrentTopObject): CityDomainModel
}