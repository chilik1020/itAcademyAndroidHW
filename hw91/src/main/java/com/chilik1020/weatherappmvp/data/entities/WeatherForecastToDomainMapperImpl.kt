package com.chilik1020.weatherappmvp.data.entities

import com.chilik1020.weatherappmvp.domain.models.WeatherForecastDomainModel
import com.chilik1020.weatherappmvp.domain.models.WeatherForecastToDomainMapper

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