package com.chilik1020.weatherappmvp.data.remote

import android.util.Log
import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentMapper
import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentTopObject
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastMapper
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject
import com.chilik1020.weatherappmvp.utils.LOG_TAG
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class WeatherApiImpl(
    private val okHttpClient: OkHttpClient = OkHttpClient(),
    private val requestFactory: RequestFactory = RequestFactoryImpl(),
    private val weatherCurrentMapper: WeatherCurrentMapper = WeatherCurrentMapper(),
    private val weatherForecastMapper: WeatherForecastMapper = WeatherForecastMapper()
) : WeatherApi {

    override fun getCurrentWeather(
        location: String,
        apiKey: String,
        units: String
    ): Single<WeatherCurrentTopObject> {
        val request = requestFactory.getCurrentWeatherRequest(location, apiKey, units)
        return Single.create<String> { emitter ->
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                if (response.body != null) {
                    val bodyString = (response.body as ResponseBody).string()
                    emitter.onSuccess(bodyString)
                    Log.d(LOG_TAG, bodyString)
                } else {
                    emitter.onError(Throwable("Empty body"))
                }
            } else {
                emitter.onError(Throwable("API ERROR ${response.code}"))
            }
        }
            .map { data -> weatherCurrentMapper(data) }
            .subscribeOn(Schedulers.io())
    }

    override fun getHourlyForecastWeather(
        lat: String,
        lon: String,
        apiKey: String,
        units: String
    ): Single<WeatherForecastTopObject> {
        val request = requestFactory.getHourlyWeatherForecastRequest(lat, lon, apiKey, units)
        return Single.create<String> { emitter ->
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                if (response.body != null) {
                    val bodyString = (response.body as ResponseBody).string()
                    emitter.onSuccess(bodyString)
                    Log.d(LOG_TAG, bodyString)
                } else {
                    emitter.onError(Throwable("Empty body"))
                }
            } else {
                emitter.onError(Throwable("API ERROR ${response.code}"))
            }
        }
            .map { data -> weatherForecastMapper(data) }
            .subscribeOn(Schedulers.io())
    }
}