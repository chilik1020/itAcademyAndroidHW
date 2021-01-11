package com.chilik1020.weatherappmvvm.data.entities

import com.chilik1020.weatherappmvvm.domain.models.CityDomainFromWeatherCurrentMapper
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class CityDomainFromWeatherCurrentMapperImpl : CityDomainFromWeatherCurrentMapper {
    override fun map(weatherCurrent: WeatherCurrentTop) = CityDomainModel(
        name = weatherCurrent.name,
        lat = weatherCurrent.coord.lat,
        lon = weatherCurrent.coord.lon,
        isCurrentCity = false
    )
}