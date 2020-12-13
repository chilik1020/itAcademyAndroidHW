package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

interface CityListUseCase {

    fun interface OnFinished {
        fun onResponse(result: Result<List<CityDomainModel>>)
    }

    fun getCities(listener: OnFinished)
}