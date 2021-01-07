package com.chilik1020.weatherappmvvm.presentation.models

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class CityUiToDomainMapperImpl : CityUiToDomainMapper {
    override fun map(cityUiModel: CityUiModel) = CityDomainModel(
        name = cityUiModel.name,
        lat = cityUiModel.lat,
        lon = cityUiModel.lon,
        isCurrentCity = cityUiModel.isCurrentCity
    )
}