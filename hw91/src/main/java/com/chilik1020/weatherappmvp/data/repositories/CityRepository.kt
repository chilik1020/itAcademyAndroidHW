package com.chilik1020.weatherappmvp.data.repositories

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface CityRepository {

    fun getActiveCity(): Maybe<CityDomainModel>
    fun getCities(): Flowable<List<CityDomainModel>>
    fun addCity(city: CityDomainModel): Maybe<Long>
    fun setCityAsActive(city: CityDomainModel): Single<Int>
}