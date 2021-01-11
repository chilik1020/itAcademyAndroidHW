package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import io.reactivex.Flowable

interface CityListUseCase {
    fun getCities(): Flowable<List<CityDomainModel>>
}