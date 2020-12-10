package com.chilik1020.weatherappmvp.di

import android.content.Context
import android.content.SharedPreferences
import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentMapper
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastMapper
import com.chilik1020.weatherappmvp.data.remote.RequestFactory
import com.chilik1020.weatherappmvp.data.remote.RequestFactoryImpl
import com.chilik1020.weatherappmvp.data.remote.WeatherApi
import com.chilik1020.weatherappmvp.data.remote.WeatherApiImpl
import com.chilik1020.weatherappmvp.domain.WeatherContract
import com.chilik1020.weatherappmvp.domain.WeatherPresenter
import com.chilik1020.weatherappmvp.utils.SHARED_PREF_NAME
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
}

val remoteModule = module {
    factory<RequestFactory> { RequestFactoryImpl() }
    factory<OkHttpClient> { OkHttpClient() }
    factory { WeatherCurrentMapper() }
    factory { WeatherForecastMapper() }
    factory<WeatherApi> { WeatherApiImpl(get(), get(), get(), get()) }
}

val domainModule = module {
    single<WeatherContract.Presenter> { WeatherPresenter(get()) }
}