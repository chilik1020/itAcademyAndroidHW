package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.repositories.CityRepository
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class CityAsActiveUseCaseImpl(private val cityRepository: CityRepository) : CityAsActiveUseCase {
    override fun setCityAsActive(city: CityDomainModel) = cityRepository.setCityAsActive(city)
}