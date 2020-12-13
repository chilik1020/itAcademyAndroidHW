package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

interface CityActiveUseCase {
    fun interface OnFinished {
        fun onResponse(result: Result<CityDomainModel>)
    }

    fun getActiveCity(listener: OnFinished)
}