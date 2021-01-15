package com.chilik1020.weatherappmvp.presentation.models

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

fun interface CityDomainToUiMapper {
    fun map(data: CityDomainModel): CityUiModel
}