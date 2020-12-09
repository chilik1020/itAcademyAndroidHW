package com.chilik1020.weatherappmvp.model.remote

import com.chilik1020.weatherappmvp.utils.REQUEST_BASE_URL
import okhttp3.Request


class RequestFactoryImpl : RequestFactory {
    override fun getCurrentWeatherRequest(
        location: String,
        apiKey: String,
        units: String
    ): Request {
        val requestUrl = REQUEST_BASE_URL.format(location, apiKey, units)
        return Request.Builder().url(requestUrl).build()
    }
}