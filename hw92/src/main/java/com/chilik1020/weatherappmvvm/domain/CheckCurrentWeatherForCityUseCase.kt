package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

interface CheckCurrentWeatherForCityUseCase {

    fun interface OnFinished {
        fun onResponse(result: Result<CityDomainModel>)
    }

    fun getCurrentWeather(location: String, listener: OnFinished)
}