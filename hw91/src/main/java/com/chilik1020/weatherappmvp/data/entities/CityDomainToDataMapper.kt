package com.chilik1020.weatherappmvp.data.entities

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

fun interface CityDomainToDataMapper {
    fun map(data: CityDomainModel): City
}