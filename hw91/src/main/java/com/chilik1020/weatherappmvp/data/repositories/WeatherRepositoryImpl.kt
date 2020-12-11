package com.chilik1020.weatherappmvp.data.repositories

import android.content.SharedPreferences
import com.chilik1020.weatherappmvp.data.entities.Units
import com.chilik1020.weatherappmvp.data.remote.WeatherApi
import com.chilik1020.weatherappmvp.domain.CurrentWeatherUseCase
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.domain.models.WeatherForecastToDomainMapper
import com.chilik1020.weatherappmvp.utils.API_KEY
import com.chilik1020.weatherappmvp.utils.UNITS_SYSTEM
import io.reactivex.disposables.CompositeDisposable

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val pref: SharedPreferences,
    private val forecastMapper: WeatherForecastToDomainMapper
) : WeatherRepository {

    private val disposables = CompositeDisposable()

    override fun getCurrentWeather(
        location: String,
        listener: CurrentWeatherUseCase.OnFinished
    ) {
        val units = pref.getString(UNITS_SYSTEM, Units.METRIC.value) ?: Units.METRIC.value
        val subs = weatherApi.getCurrentWeather(location, API_KEY, units)
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
            .subscribe(
                { listener.onResponse(Result.Success(it)) },
                { listener.onResponse(Result.Failure(it)) }
            )
        disposables.add(subs)
    }
}