package com.chilik1020.weatherappmvvm.presentation.models

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

fun interface CityDomainToUiMapper {
    fun map(data: CityDomainModel): CityUiModel
}