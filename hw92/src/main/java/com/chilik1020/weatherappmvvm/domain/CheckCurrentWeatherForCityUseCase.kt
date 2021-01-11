package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import io.reactivex.Single

interface CheckCurrentWeatherForCityUseCase {
    fun getCityIfCurrentWeatherPresented(location: String): Single<CityDomainModel>
}