package com.chilik1020.weatherappmvp.model.remote

import com.chilik1020.weatherappmvp.utils.REQUEST_CURRENT_BASE_URL
import com.chilik1020.weatherappmvp.utils.REQUEST_FORECAST_BASE_URL
import okhttp3.Request


class RequestFactoryImpl : RequestFactory {
    override fun getCurrentWeatherRequest(
        location: String,
        apiKey: String,
        units: String
    ): Request {
        val requestUrl = REQUEST_CURRENT_BASE_URL.format(location, apiKey, units)
        return Request.Builder().url(requestUrl).build()
    }

    override fun getHourlyWeatherForecastRequest(
        lat: String,
        lon: String,
        apiKey: String,
        units: String
    ): Request {
        val requestUrl = REQUEST_FORECAST_BASE_URL.format(lat, lon, apiKey, units)
        return Request.Builder().url(requestUrl).build()
    }
}