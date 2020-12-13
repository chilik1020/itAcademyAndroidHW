package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

interface CityAddUseCase {

    fun interface OnFinished {
        fun onResponse(data: Result<Long>)
    }

    fun addCity(city: CityDomainModel, listener: OnFinished)
}