package com.chilik1020.weatherappmvp.model.remote

import com.chilik1020.weatherappmvp.model.entities.Units
import com.chilik1020.weatherappmvp.model.entities.WeatherTopObject
import com.chilik1020.weatherappmvp.utils.API_KEY
import io.reactivex.Single

interface WeatherApi {

    fun getCurrentWeather(
        location: String = "Minsk",
        apiKey: String = API_KEY,
        units: String = Units.METRIC.value
    ) : Single<WeatherTopObject>
}