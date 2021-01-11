package com.chilik1020.weatherappmvp.data.remote

interface ApiKeyProvider {
    fun getWeatherApiKey(): String
}