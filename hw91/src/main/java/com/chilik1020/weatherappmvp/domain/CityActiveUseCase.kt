package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

interface CityActiveUseCase {
    fun interface OnFinished {
        fun onResponse(result: Result<CityDomainModel>)
    }

    fun getActiveCity(listener: CityActiveUseCase.OnFinished)
}