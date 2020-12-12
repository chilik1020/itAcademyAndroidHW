package com.chilik1020.weatherappmvp.data.entities

import com.chilik1020.weatherappmvp.domain.models.CityDomainFromWeatherCurrentMapper
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class CityDomainFromWeatherCurrentMapperImpl : CityDomainFromWeatherCurrentMapper {
    override fun map(weatherCurrent: WeatherCurrentTopObject): CityDomainModel {
        return CityDomainModel(
            name = weatherCurrent.name,
            lat = weatherCurrent.coord.lat,
            lon = weatherCurrent.coord.lon,
            isCurrentCity = false
        )
    }
}