package com.chilik1020.weatherappmvp.model.remote

import com.chilik1020.weatherappmvp.utils.API_KEY

interface WeatherApi {

    fun getCurrentWeather(
        location: String = "Minsk",
        apiKey: String = API_KEY
    )
}