package com.chilik1020.weatherappmvp.di

import android.content.Context
import android.content.SharedPreferences
import com.chilik1020.weatherappmvp.data.entities.JsonToWeatherCurrentMapper
import com.chilik1020.weatherappmvp.data.entities.JsonToWeatherForecastMapper
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastToDomainMapperImpl
import com.chilik1020.weatherappmvp.data.remote.RequestFactory
import com.chilik1020.weatherappmvp.data.remote.RequestFactoryImpl
import com.chilik1020.weatherappmvp.data.remote.WeatherApi
import com.chilik1020.weatherappmvp.data.remote.WeatherApiImpl
import com.chilik1020.weatherappmvp.data.repositories.WeatherRepository
import com.chilik1020.weatherappmvp.data.repositories.WeatherRepositoryImpl
import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.CityAddUseCaseImpl
import com.chilik1020.weatherappmvp.domain.CityListUseCase
import com.chilik1020.weatherappmvp.domain.CityListUseCaseImpl
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCaseImpl
import com.chilik1020.weatherappmvp.presentation.city.CityContract
import com.chilik1020.weatherappmvp.presentation.city.CityPresenter
import com.chilik1020.weatherappmvp.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvp.presentation.models.CityDomainToUiMapperImpl
import com.chilik1020.weatherappmvp.presentation.models.CityUiToDomainMapper
import com.chilik1020.weatherappmvp.presentation.models.CityUiToDomainMapperImpl
import com.chilik1020.weatherappmvp.presentation.models.WeatherForecastDomainToUiMapper
import com.chilik1020.weatherappmvp.presentation.models.WeatherForecastDomainToUiMapperImpl
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

val presentationModule = module {
    factory<WeatherForecastDomainToUiMapper> { WeatherForecastDomainToUiMapperImpl() }
    factory<CityDomainToUiMapper> { CityDomainToUiMapperImpl() }
    factory<CityUiToDomainMapper> { CityUiToDomainMapperImpl() }

    single<WeatherContract.Presenter> {
        WeatherPresenter(
            useCase = get(),
            forecastDomainToUiMapper = get()
        )
    }
    single<CityContract.Presenter> {
        CityPresenter(
            cityListUseCase = get(),
            cityAddUseCase = get(),
            currentWeatherUseCase = get(),
            cityDomainToUiMapper = get(),
            cityUiToDomainMapper = get()
        )
    }
}

val dataModule = module {
    factory<RequestFactory> { RequestFactoryImpl() }
    factory<OkHttpClient> { OkHttpClient() }
    factory { JsonToWeatherCurrentMapper() }
    factory { JsonToWeatherForecastMapper() }
    factory { WeatherForecastToDomainMapperImpl() }
    factory<WeatherApi> { WeatherApiImpl(get(), get(), get(), get()) }
    factory<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherApi = get(),
            pref = get(),
            forecastMapper = get()
        )
    }
}

val domainModule = module {
    factory<ForecastWeatherUseCase> { ForecastWeatherUseCaseImpl(weatherRepository = get()) }
    factory<CityListUseCase> { CityListUseCaseImpl(cityRepository = get()) }
    factory<CityAddUseCase> { CityAddUseCaseImpl(cityRepository = get()) }
}

