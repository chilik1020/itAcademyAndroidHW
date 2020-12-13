package com.chilik1020.weatherappmvvm.data.repositories

import android.util.Log
import com.chilik1020.weatherappmvvm.data.entities.CityDomainToDataMapper
import com.chilik1020.weatherappmvvm.data.local.CityDao
import com.chilik1020.weatherappmvvm.domain.CityActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityAddUseCase
import com.chilik1020.weatherappmvvm.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityListUseCase
import com.chilik1020.weatherappmvvm.domain.Result
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import com.chilik1020.weatherappmvvm.domain.models.CityToDomainMapper
import com.chilik1020.weatherappmvvm.utils.LOG_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val cityDomainToDataMapper: CityDomainToDataMapper,
    private val cityToDomainMapper: CityToDomainMapper
) : CityRepository {

    private val disposables = CompositeDisposable()

    override fun getActiveCity(listener: CityActiveUseCase.OnFinished) {
        val subs = cityDao.getActiveCity()
            .map { cityToDomainMapper.map(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listener.onResponse(Result.Success(it))
                },
                {
                    listener.onResponse(Result.Failure(it))
                }
            )

        disposables.add(subs)
    }

    override fun getCities(listener: CityListUseCase.OnFinished) {
        val subs = cityDao.getCities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cityList ->
                    val list =
                        cityList.map { city -> cityToDomainMapper.map(city) }.toList()
                    listener.onResponse(Result.Success(list))
                },
                {
                    listener.onResponse(Result.Failure(it))
                }
            )

        disposables.add(subs)
    }

    override fun addCity(city: CityDomainModel, listener: CityAddUseCase.OnFinished) {
        val subs = cityDao.addCity(cityDomainToDataMapper.map(city))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listener.onResponse(Result.Success(it))
                },
                {
                    listener.onResponse(Result.Failure(it))
                }
            )

        disposables.add(subs)
    }

    override fun setCityAsActive(city: CityDomainModel, listener: CityAsActiveUseCase.OnFinished) {
        val subs = cityDao.clearIsActiveCityField()
            .flatMap { cityDao.updateCityStatusByName(city.name) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(LOG_TAG, "clear completed")
                    listener.onResponse(Result.Success(it))
                },
                {
                    Log.d(LOG_TAG, "clear error")
                    listener.onResponse(Result.Failure(it))
                }
            )

        disposables.add(subs)
    }

    override fun close() {
        disposables.dispose()
    }
}