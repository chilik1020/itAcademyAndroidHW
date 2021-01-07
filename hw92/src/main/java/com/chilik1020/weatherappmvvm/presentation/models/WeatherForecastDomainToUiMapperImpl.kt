package com.chilik1020.weatherappmvvm.presentation.models

import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastDomainModel

class WeatherForecastDomainToUiMapperImpl : WeatherForecastDomainToUiMapper {
    override fun map(data: WeatherForecastDomainModel) = WeatherForecastUiModel(
        data.lat,
        data.lon,
        data.current,
        data.hourlyList
    )
}