package com.chilik1020.weatherappmvp.model.remote

import com.chilik1020.weatherappmvp.model.entities.WeatherObjectMapper
import com.chilik1020.weatherappmvp.model.entities.WeatherTopObject
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class WeatherApiImpl(
    private val okHttpClient: OkHttpClient = OkHttpClient(),
    private val requestFactory: RequestFactory = RequestFactoryImpl(),
    private val mapper: WeatherObjectMapper = WeatherObjectMapper()
) : WeatherApi {

    override fun getCurrentWeather(
        location: String,
        apiKey: String,
        units: String
    ): Single<WeatherTopObject> {
        val request = requestFactory.getCurrentWeatherRequest(location, apiKey, units)
        return Single.create<String> { emitter ->
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                if (response.body != null) {
                    emitter.onSuccess((response.body as ResponseBody).string())
                } else {
                    emitter.onError(Throwable("Empty body"))
                }
            } else {
                emitter.onError(Throwable("API ERROR ${response.code}"))
            }
        }
            .map { data -> mapper(data) }
            .subscribeOn(Schedulers.io())
    }
}