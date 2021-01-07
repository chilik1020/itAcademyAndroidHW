package com.chilik1020.weatherappmvp.data.entities

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import com.chilik1020.weatherappmvp.domain.models.CityToDomainMapper

class CityToDomainMapperImpl : CityToDomainMapper {
    override fun map(city: City) = CityDomainModel(
        name = city.name,
        lat = city.lat,
        lon = city.lon,
        isCurrentCity = city.isCurrentCity
    )
}