package com.chilik1020.weatherappmvp.model.remote

import okhttp3.Request

interface RequestFactory {

    fun getCurrentWeatherRequest(
        location: String,
        apiKey: String,
        units: String
    ): Request
}