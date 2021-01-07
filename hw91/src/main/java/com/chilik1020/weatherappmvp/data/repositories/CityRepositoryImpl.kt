package com.chilik1020.weatherappmvp.data.repositories

import com.chilik1020.weatherappmvp.data.entities.CityDomainToDataMapper
import com.chilik1020.weatherappmvp.data.local.CityDao
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel
import com.chilik1020.weatherappmvp.domain.models.CityToDomainMapper
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val cityDomainToDataMapper: CityDomainToDataMapper,
    private val cityToDomainMapper: CityToDomainMapper
) : CityRepository {

    override fun getActiveCity(): Maybe<CityDomainModel> =
        cityDao.getActiveCity()
            .map { cityToDomainMapper.map(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getCities() =
        cityDao.getCities()
            .map { it.map { city -> cityToDomainMapper.map(city) }.toList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addCity(city: CityDomainModel) =
        cityDao.addCity(cityDomainToDataMapper.map(city))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun setCityAsActive(city: CityDomainModel) =
        cityDao.clearIsActiveCityField()
            .flatMap { cityDao.updateCityStatusByName(city.name) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}