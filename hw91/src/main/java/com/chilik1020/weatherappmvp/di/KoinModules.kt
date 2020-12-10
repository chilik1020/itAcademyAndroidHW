package com.chilik1020.weatherappmvp.di

import android.content.Context
import android.content.SharedPreferences
import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentMapper
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastMapper
import com.chilik1020.weatherappmvp.data.remote.RequestFactory
import com.chilik1020.weatherappmvp.data.remote.RequestFactoryImpl
import com.chilik1020.weatherappmvp.data.remote.WeatherApi
import com.chilik1020.weatherappmvp.data.remote.WeatherApiImpl
import com.chilik1020.weatherappmvp.data.repositories.WeatherRepository
import com.chilik1020.weatherappmvp.data.repositories.WeatherRepositoryImpl
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCaseImpl
import com.chilik1020.weatherappmvp.presentation.weather.WeatherContract
import com.chilik1020.weatherappmvp.presentation.weather.WeatherPresenter
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

val presenterModule = module {
    single<WeatherContract.Presenter> { WeatherPresenter(get()) }
}

val dataModule = module {
    factory<RequestFactory> { RequestFactoryImpl() }
    factory<OkHttpClient> { OkHttpClient() }
    factory { WeatherCurrentMapper() }
    factory { WeatherForecastMapper() }
    factory<WeatherApi> { WeatherApiImpl(get(), get(), get(), get()) }
    factory<WeatherRepository> { WeatherRepositoryImpl(weatherApi = get(), pref = get()) }
}

val useCaseModule = module {
    factory<ForecastWeatherUseCase> { ForecastWeatherUseCaseImpl(weatherRepository = get()) }
}

