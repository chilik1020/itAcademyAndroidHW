package com.chilik1020.weatherappmvp.data.repositories

import com.chilik1020.weatherappmvp.domain.CityActiveUseCase
import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvp.domain.CityListUseCase
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

interface CityRepository {

    fun getActiveCity(listener: CityActiveUseCase.OnFinished)
    fun getCities(listener: CityListUseCase.OnFinished)
    fun addCity(city: CityDomainModel, listener: CityAddUseCase.OnFinished)
    fun setCityAsActive(city: CityDomainModel, listener: CityAsActiveUseCase.OnFinished)
}