package com.chilik1020.weatherappmvvm.data.remote

interface ApiKeyProvider {
    fun getWeatherApiKey(): String
}