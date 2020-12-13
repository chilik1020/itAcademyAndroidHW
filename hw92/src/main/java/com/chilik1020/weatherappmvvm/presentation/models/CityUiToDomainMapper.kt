package com.chilik1020.weatherappmvvm.presentation.models

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

interface CityUiToDomainMapper {
    fun map(cityUiModel: CityUiModel): CityDomainModel
}