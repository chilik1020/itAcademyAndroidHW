package com.chilik1020.weatherappmvp.presentation.models

import com.chilik1020.weatherappmvp.domain.models.WeatherForecastDomainModel

class WeatherForecastDomainToUiMapperImpl : WeatherForecastDomainToUiMapper {
    override fun map(data: WeatherForecastDomainModel): WeatherForecastUiModel {
        return WeatherForecastUiModel(
            data.lat,
            data.lon,
            data.current,
            data.hourlyList
        )
    }
}