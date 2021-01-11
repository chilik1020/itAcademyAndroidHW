package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import io.reactivex.Single

interface CityAsActiveUseCase {
    fun setCityAsActive(city: CityDomainModel): Single<Int>
}