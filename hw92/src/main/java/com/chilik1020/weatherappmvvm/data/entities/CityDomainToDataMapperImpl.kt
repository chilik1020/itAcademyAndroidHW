package com.chilik1020.weatherappmvvm.data.entities

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class CityDomainToDataMapperImpl : CityDomainToDataMapper {
    override fun map(data: CityDomainModel) = City(
            id = 0,
            name = data.name,
            lat = data.lat,
            lon = data.lon,
            isCurrentCity = data.isCurrentCity
        )
}