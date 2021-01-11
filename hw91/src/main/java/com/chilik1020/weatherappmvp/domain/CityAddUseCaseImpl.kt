package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.repositories.CityRepository
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class CityAddUseCaseImpl(private val cityRepository: CityRepository) : CityAddUseCase {
    override fun addCity(city: CityDomainModel) =
        cityRepository.addCity(city)
}