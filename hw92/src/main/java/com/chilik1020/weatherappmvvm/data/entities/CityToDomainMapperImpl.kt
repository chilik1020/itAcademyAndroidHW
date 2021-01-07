package com.chilik1020.weatherappmvvm.data.entities

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import com.chilik1020.weatherappmvvm.domain.models.CityToDomainMapper

class CityToDomainMapperImpl : CityToDomainMapper {
    override fun map(city: City) = CityDomainModel(
        name = city.name,
        lat = city.lat,
        lon = city.lon,
        isCurrentCity = city.isCurrentCity
    )
}