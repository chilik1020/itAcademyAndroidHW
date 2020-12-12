package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

interface CityListUseCase {

    fun interface OnFinished {
        fun onResponse(result: Result<List<CityDomainModel>>)
    }

    fun getCities(listener: CityListUseCase.OnFinished)
}