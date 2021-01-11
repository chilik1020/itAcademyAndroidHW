package com.chilik1020.weatherappmvvm.data.remote

import android.util.Log
import com.chilik1020.weatherappmvvm.data.entities.JsonToWeatherCurrentMapper
import com.chilik1020.weatherappmvvm.data.entities.JsonToWeatherForecastMapper
import com.chilik1020.weatherappmvvm.data.entities.WeatherCurrentTop
import com.chilik1020.weatherappmvvm.data.entities.WeatherForecastTop
import com.chilik1020.weatherappmvvm.utils.LOG_TAG
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class WeatherApiImpl(
    private val okHttpClient: OkHttpClient,
    private val requestFactory: RequestFactory,
    private val weatherCurrentMapper: JsonToWeatherCurrentMapper,
    private val weatherForecastMapper: JsonToWeatherForecastMapper
) : WeatherApi {

    override fun getCurrentWeather(
        location: String,
        units: String
    ): Single<WeatherCurrentTop> {
        val request = requestFactory.getCurrentWeatherRequest(location, units)
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
        units: String
    ): Single<WeatherForecastTop> {
        val request = requestFactory.getHourlyWeatherForecastRequest(lat, lon, units)
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