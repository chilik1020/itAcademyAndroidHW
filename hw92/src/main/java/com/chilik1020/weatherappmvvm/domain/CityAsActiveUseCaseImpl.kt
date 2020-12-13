package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.data.repositories.CityRepository
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class CityAsActiveUseCaseImpl(private val cityRepository: CityRepository) :
    CityAsActiveUseCase {

    override fun setCityAsActive(
        city: CityDomainModel,
        listener: CityAsActiveUseCase.OnFinished
    ) {
        cityRepository.setCityAsActive(city, listener)
    }
}