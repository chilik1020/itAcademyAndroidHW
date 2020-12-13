package com.chilik1020.weatherappmvvm.data.repositories

import com.chilik1020.weatherappmvvm.domain.CityActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityAddUseCase
import com.chilik1020.weatherappmvvm.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityListUseCase
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

interface CityRepository {

    fun getActiveCity(listener: CityActiveUseCase.OnFinished)
    fun getCities(listener: CityListUseCase.OnFinished)
    fun addCity(city: CityDomainModel, listener: CityAddUseCase.OnFinished)
    fun setCityAsActive(city: CityDomainModel, listener: CityAsActiveUseCase.OnFinished)
    fun close()
}