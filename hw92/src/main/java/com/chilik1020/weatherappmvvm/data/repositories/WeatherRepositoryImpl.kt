package com.chilik1020.weatherappmvvm.data.repositories

import android.content.SharedPreferences
import com.chilik1020.weatherappmvvm.data.entities.Units
import com.chilik1020.weatherappmvvm.data.remote.WeatherApi
import com.chilik1020.weatherappmvvm.domain.models.CityDomainFromWeatherCurrentMapper
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastDomainModel
import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastToDomainMapper
import com.chilik1020.weatherappmvvm.utils.UNITS_SYSTEM
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val pref: SharedPreferences,
    private val cityDomainFromWeatherCurrentMapper: CityDomainFromWeatherCurrentMapper,
    private val forecastMapper: WeatherForecastToDomainMapper
) : WeatherRepository {

    override fun getCityIfCurrentWeatherPresented(
        location: String
    ): Single<CityDomainModel> {
        val units = pref.getString(UNITS_SYSTEM, Units.METRIC.value) ?: Units.METRIC.value
        return weatherApi.getCurrentWeather(location, units)
            .observeOn(AndroidSchedulers.mainThread())
            .map { cityDomainFromWeatherCurrentMapper.map(it) }
    }

    override fun getHourlyForecastWeather(
        lat: String,
        lon: String,
    ): Single<WeatherForecastDomainModel> {
        val units = pref.getString(UNITS_SYSTEM, Units.METRIC.value) ?: Units.METRIC.value
        return weatherApi.getHourlyForecastWeather(lat, lon, units)
            .map { forecastMapper.map(it) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}