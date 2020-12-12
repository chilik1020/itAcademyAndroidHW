package com.chilik1020.weatherappmvp.presentation.models

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class CityUiToDomainMapperImpl : CityUiToDomainMapper {
    override fun map(cityUiModel: CityUiModel): CityDomainModel {
        return CityDomainModel(
            name = cityUiModel.name,
            lat = cityUiModel.lat,
            lon = cityUiModel.lon,
            isCurrentCity = cityUiModel.isCurrentCity
        )
    }
}