package com.chilik1020.weatherappmvp.presentation.models

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class CityDomainToUiMapperImpl : CityDomainToUiMapper {
    override fun map(data: CityDomainModel): CityUiModel {
        return CityUiModel(
            name = data.name,
            lat = data.lat,
            lon = data.lon,
            isCurrentCity = data.isCurrentCity
        )
    }
}