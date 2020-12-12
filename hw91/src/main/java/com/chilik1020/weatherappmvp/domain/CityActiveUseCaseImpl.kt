package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.repositories.CityRepository

class CityActiveUseCaseImpl(private val cityRepository: CityRepository) : CityActiveUseCase {
    override fun getActiveCity(listener: CityActiveUseCase.OnFinished) {
        cityRepository.getActiveCity(listener)
    }
}