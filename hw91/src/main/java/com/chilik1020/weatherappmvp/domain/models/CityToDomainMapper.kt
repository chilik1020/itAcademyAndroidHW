package com.chilik1020.weatherappmvp.domain.models

import com.chilik1020.weatherappmvp.data.entities.City

fun interface CityToDomainMapper {
    fun map(city: City): CityDomainModel
}