package com.chilik1020.weatherappmvp.data.repositories

import com.chilik1020.weatherappmvp.data.local.CityDao
import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.CityListUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import com.chilik1020.weatherappmvp.domain.models.CityToDomainMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val cityToDomainMapper: CityToDomainMapper
) : CityRepository {

    override fun getCities(listener: CityListUseCase.OnFinished) {
        cityDao.getCities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cities ->
                cities.forEach {  cityToDomainMapper.map(it)}
            }.toList()
            .subscribe(
                {
                    listener.onResponse(Result.Success(it)) },
                {}
            )
    }

    override fun addCity(city: CityDomainModel, listener: CityAddUseCase.OnFinished) {
        TODO("Not yet implemented")
    }
}