package com.chilik1020.weatherappmvp.data.remote

import com.chilik1020.weatherappmvp.utils.API_KEY

class ApiKeyProviderImpl : ApiKeyProvider {
    override fun getWeatherApiKey() = API_KEY
}