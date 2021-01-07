package com.chilik1020.weatherappmvp.data.entities

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class CityDomainToDataMapperImpl : CityDomainToDataMapper {
    override fun map(data: CityDomainModel) = City(
        id = 0,
        name = data.name,
        lat = data.lat,
        lon = data.lon,
        isCurrentCity = data.isCurrentCity
    )
}