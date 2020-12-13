package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

interface CityAsActiveUseCase {

    fun interface OnFinished {
        fun onResponse(result: Result<Int>)
    }

    fun setCityAsActive(city: CityDomainModel, listener: OnFinished)
}