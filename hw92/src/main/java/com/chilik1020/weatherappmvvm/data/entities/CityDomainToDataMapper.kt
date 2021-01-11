package com.chilik1020.weatherappmvvm.data.entities

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

fun interface CityDomainToDataMapper {
    fun map(data: CityDomainModel): City
}