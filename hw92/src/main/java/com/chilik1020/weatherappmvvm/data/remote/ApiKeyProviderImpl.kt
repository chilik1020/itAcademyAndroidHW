package com.chilik1020.weatherappmvvm.data.remote

import com.chilik1020.weatherappmvvm.utils.API_KEY

class ApiKeyProviderImpl : ApiKeyProvider {
    override fun getWeatherApiKey() = API_KEY
}