package com.chilik1020.weatherappmvvm.domain

import com.chilik1020.weatherappmvvm.data.repositories.CityRepository

class CityListUseCaseImpl(private val cityRepository: CityRepository) : CityListUseCase {
    override fun getCities(listener: CityListUseCase.OnFinished) {
        cityRepository.getCities(listener)
    }
}