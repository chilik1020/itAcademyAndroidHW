package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

interface CityAsActiveUseCase {

    fun interface OnFinished {
        fun onResponse(result: Result<Int>)
    }

    fun setCityAsActive(city: CityDomainModel, listener: CityAsActiveUseCase.OnFinished)
}