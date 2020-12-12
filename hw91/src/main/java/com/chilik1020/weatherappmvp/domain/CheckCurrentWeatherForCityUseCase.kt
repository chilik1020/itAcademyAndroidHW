package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

interface CheckCurrentWeatherForCityUseCase {

    fun interface OnFinished {
        fun onResponse(result: Result<CityDomainModel>)
    }

    fun getCurrentWeather(location: String, listener: CheckCurrentWeatherForCityUseCase.OnFinished)
}