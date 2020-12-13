package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.data.repositories.CityRepository
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class CityAddUseCaseImpl(private val cityRepository: CityRepository) : CityAddUseCase {
    override fun addCity(city: CityDomainModel, listener: CityAddUseCase.OnFinished) {
        cityRepository.addCity(city, listener)
    }
}