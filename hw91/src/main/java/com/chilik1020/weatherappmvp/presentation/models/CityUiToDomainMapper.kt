package com.chilik1020.weatherappmvp.presentation.models

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

interface CityUiToDomainMapper {
    fun map(cityUiModel: CityUiModel): CityDomainModel
}