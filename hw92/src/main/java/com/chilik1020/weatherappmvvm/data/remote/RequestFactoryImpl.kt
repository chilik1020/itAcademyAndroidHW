package com.chilik1020.weatherappmvvm.data.remote

import com.chilik1020.weatherappmvvm.utils.REQUEST_CURRENT_BASE_URL
import com.chilik1020.weatherappmvvm.utils.REQUEST_FORECAST_BASE_URL
import okhttp3.Request

class RequestFactoryImpl(
    private val apiKetProvider: ApiKeyProvider
) : RequestFactory {
    override fun getCurrentWeatherRequest(
        location: String,
        units: String
    ): Request {
        val apiKey = apiKetProvider.getWeatherApiKey()
        val requestUrl = REQUEST_CURRENT_BASE_URL.format(location, apiKey, units)
        return Request.Builder().url(requestUrl).build()
    }

    override fun getHourlyWeatherForecastRequest(
        lat: String,
        lon: String,
        units: String
    ): Request {
        val apiKey = apiKetProvider.getWeatherApiKey()
        val requestUrl = REQUEST_FORECAST_BASE_URL.format(lat, lon, apiKey, units)
        return Request.Builder().url(requestUrl).build()
    }
}