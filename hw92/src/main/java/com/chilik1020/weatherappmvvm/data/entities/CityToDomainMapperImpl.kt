package com.chilik1020.weatherappmvvm.data.entities

import com.chilik1020.weatherappmvvm.domain.models.CityToDomainMapper
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class CityToDomainMapperImpl : CityToDomainMapper {
    override fun map(city: City): CityDomainModel {
        return CityDomainModel(
            name = city.name,
            lat = city.lat,
            lon = city.lon,
            isCurrentCity = city.isCurrentCity
        )
    }
}