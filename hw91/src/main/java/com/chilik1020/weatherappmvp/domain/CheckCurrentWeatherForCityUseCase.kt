package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import io.reactivex.Single

interface CheckCurrentWeatherForCityUseCase {
    fun getCityIfCurrentWeatherPresented(location: String): Single<CityDomainModel>
}