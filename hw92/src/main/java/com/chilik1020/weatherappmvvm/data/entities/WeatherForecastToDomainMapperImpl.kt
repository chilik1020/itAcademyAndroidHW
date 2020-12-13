package com.chilik1020.weatherappmvvm.data.entities

import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastDomainModel
import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastToDomainMapper

class WeatherForecastToDomainMapperImpl : WeatherForecastToDomainMapper {
    override fun map(data: WeatherForecastTopObject): WeatherForecastDomainModel {
        return WeatherForecastDomainModel(
            data.lat,
            data.lon,
            data.current,
            data.hourlyList
        )
    }
}