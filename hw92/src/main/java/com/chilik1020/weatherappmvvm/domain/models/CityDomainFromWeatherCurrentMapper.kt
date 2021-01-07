package com.chilik1020.weatherappmvvm.domain.models

import com.chilik1020.weatherappmvvm.data.entities.WeatherCurrentTop

fun interface CityDomainFromWeatherCurrentMapper {
    fun map(weatherCurrent: WeatherCurrentTop): CityDomainModel
}