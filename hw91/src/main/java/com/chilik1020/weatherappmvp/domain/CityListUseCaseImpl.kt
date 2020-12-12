package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.repositories.CityRepository

class CityListUseCaseImpl(private val cityRepository: CityRepository) : CityListUseCase {
    override fun getCities(listener: CityListUseCase.OnFinished) {
        cityRepository.getCities(listener)
    }
}