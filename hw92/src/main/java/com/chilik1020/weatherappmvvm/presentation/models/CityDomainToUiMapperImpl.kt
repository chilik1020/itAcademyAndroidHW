package com.chilik1020.weatherappmvvm.presentation.models

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class CityDomainToUiMapperImpl : CityDomainToUiMapper {
    override fun map(data: CityDomainModel) = CityUiModel(
        name = data.name,
        lat = data.lat,
        lon = data.lon,
        isCurrentCity = data.isCurrentCity
    )
}