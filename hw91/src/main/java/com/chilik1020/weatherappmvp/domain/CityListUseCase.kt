package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import io.reactivex.Flowable

interface CityListUseCase {
    fun getCities(): Flowable<List<CityDomainModel>>
}