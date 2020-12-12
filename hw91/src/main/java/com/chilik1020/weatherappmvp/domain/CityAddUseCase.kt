package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

interface CityAddUseCase {

    fun interface OnFinished {
        fun onResponse(data: Result<Long>)
    }

    fun addCity(city: CityDomainModel, listener: CityAddUseCase.OnFinished)
}