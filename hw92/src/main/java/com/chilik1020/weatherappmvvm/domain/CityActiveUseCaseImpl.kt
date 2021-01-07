package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.data.repositories.CityRepository

class CityActiveUseCaseImpl(private val cityRepository: CityRepository) : CityActiveUseCase {
    override fun getActiveCity() = cityRepository.getActiveCity()
}