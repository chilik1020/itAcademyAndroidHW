package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import io.reactivex.Maybe

interface CityAddUseCase {
    fun addCity(city: CityDomainModel) : Maybe<Long>
}