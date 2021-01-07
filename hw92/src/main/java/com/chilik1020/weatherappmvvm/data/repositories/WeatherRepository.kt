package com.chilik1020.weatherappmvvm.data.repositories

import com.chilik1020.weatherappmvvm.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel
import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastDomainModel
import io.reactivex.Single

interface WeatherRepository {

    fun getCityIfCurrentWeatherPresented(
        location: String,
    ): Single<CityDomainModel>

    fun getHourlyForecastWeather(
        lat: String,
        lon: String,
    ): Single<WeatherForecastDomainModel>
}