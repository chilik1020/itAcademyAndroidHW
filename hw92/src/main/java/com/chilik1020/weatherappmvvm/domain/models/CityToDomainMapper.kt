package com.chilik1020.weatherappmvvm.domain.models

import com.chilik1020.weatherappmvvm.data.entities.City

fun interface CityToDomainMapper {
    fun map(city: City): CityDomainModel
}