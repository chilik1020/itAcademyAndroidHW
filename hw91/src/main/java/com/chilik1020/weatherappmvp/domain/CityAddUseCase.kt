package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import io.reactivex.Maybe

interface CityAddUseCase {
    fun addCity(city: CityDomainModel) : Maybe<Long>
}