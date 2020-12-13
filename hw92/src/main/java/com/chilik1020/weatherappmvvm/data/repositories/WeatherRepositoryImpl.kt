package com.chilik1020.weatherappmvvm.data.repositories

import android.content.SharedPreferences
import com.chilik1020.weatherappmvvm.data.entities.Units
import com.chilik1020.weatherappmvvm.data.remote.WeatherApi
import com.chilik1020.weatherappmvvm.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvvm.domain.Result
import com.chilik1020.weatherappmvvm.domain.models.CityDomainFromWeatherCurrentMapper
import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastToDomainMapper
import com.chilik1020.weatherappmvvm.utils.API_KEY
import com.chilik1020.weatherappmvvm.utils.UNITS_SYSTEM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val pref: SharedPreferences,
    private val cityDomainFromWeatherCurrentMapper: CityDomainFromWeatherCurrentMapper,
    private val forecastMapper: WeatherForecastToDomainMapper
) : WeatherRepository {

    private val disposables = CompositeDisposable()

    override fun getCurrentWeather(
        location: String,
        listener: CheckCurrentWeatherForCityUseCase.OnFinished
    ) {
        val units = pref.getString(UNITS_SYSTEM, Units.METRIC.value) ?: Units.METRIC.value
        val subs = weatherApi.getCurrentWeather(location, API_KEY, units)
            .observeOn(AndroidSchedulers.mainThread())
            .map { cityDomainFromWeatherCurrentMapper.map(it) }
            .subscribe(
                { listener.onResponse(Result.Success(it)) },
                { listener.onResponse(Result.Failure(it)) }
            )
        disposables.add(subs)
    }

    override fun getHourlyForecastWeather(
        lat: String,
        lon: String,
        listener: ForecastWeatherUseCase.OnFinished
    ) {
        val units = pref.getString(UNITS_SYSTEM, Units.METRIC.value) ?: Units.METRIC.value
        val subs = weatherApi.getHourlyForecastWeather(lat, lon, API_KEY, units)
            .map { it -> forecastMapper.map(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listener.onResponse(Result.Success(it)) },
                { listener.onResponse(Result.Failure(it)) }
            )
        disposables.add(subs)
    }

    override fun close() {
        disposables.dispose()
    }
}