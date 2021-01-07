package com.chilik1020.weatherappmvp.data.remote

import okhttp3.Request

interface RequestFactory {

    fun getCurrentWeatherRequest(
        location: String,
        units: String
    ): Request

    fun getHourlyWeatherForecastRequest(
        lat: String,
        lon: String,
        units: String
    ): Request
}