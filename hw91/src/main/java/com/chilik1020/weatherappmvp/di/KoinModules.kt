package com.chilik1020.weatherappmvp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.chilik1020.weatherappmvp.data.entities.CityDomainFromWeatherCurrentMapperImpl
import com.chilik1020.weatherappmvp.data.entities.CityDomainToDataMapper
import com.chilik1020.weatherappmvp.data.entities.CityDomainToDataMapperImpl
import com.chilik1020.weatherappmvp.data.entities.CityToDomainMapperImpl
import com.chilik1020.weatherappmvp.data.entities.JsonToWeatherCurrentMapper
import com.chilik1020.weatherappmvp.data.entities.JsonToWeatherForecastMapper
import com.chilik1020.weatherappmvp.data.entities.WeatherForecastToDomainMapperImpl
import com.chilik1020.weatherappmvp.data.local.AppDatabase
import com.chilik1020.weatherappmvp.data.remote.RequestFactory
import com.chilik1020.weatherappmvp.data.remote.RequestFactoryImpl
import com.chilik1020.weatherappmvp.data.remote.WeatherApi
import com.chilik1020.weatherappmvp.data.remote.WeatherApiImpl
import com.chilik1020.weatherappmvp.data.repositories.CityRepository
import com.chilik1020.weatherappmvp.data.repositories.CityRepositoryImpl
import com.chilik1020.weatherappmvp.data.repositories.WeatherRepository
import com.chilik1020.weatherappmvp.data.repositories.WeatherRepositoryImpl
import com.chilik1020.weatherappmvp.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvp.domain.CheckCurrentWeatherForCityUseCaseImpl
import com.chilik1020.weatherappmvp.domain.CityActiveUseCase
import com.chilik1020.weatherappmvp.domain.CityActiveUseCaseImpl
import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.CityAddUseCaseImpl
import com.chilik1020.weatherappmvp.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvp.domain.CityAsActiveUseCaseImpl
import com.chilik1020.weatherappmvp.domain.CityListUseCase
import com.chilik1020.weatherappmvp.domain.CityListUseCaseImpl
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCaseImpl
import com.chilik1020.weatherappmvp.domain.models.CityDomainFromWeatherCurrentMapper
import com.chilik1020.weatherappmvp.domain.models.CityToDomainMapper
import com.chilik1020.weatherappmvp.domain.models.WeatherForecastToDomainMapper
import com.chilik1020.weatherappmvp.presentation.addcity.AddCityContract
import com.chilik1020.weatherappmvp.presentation.addcity.AddCityPresenter
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
import com.chilik1020.weatherappmvp.utils.DATABASE_NAME
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

val roomModule = module {
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    fun provideCityDao(database: AppDatabase) = database.cityDao

    single { provideDatabase(androidApplication()) }
    single { provideCityDao(get()) }
}


val dataModule = module {
    factory<RequestFactory> { RequestFactoryImpl() }
    factory<OkHttpClient> { OkHttpClient() }
    factory { JsonToWeatherCurrentMapper() }
    factory { JsonToWeatherForecastMapper() }
    factory<WeatherForecastToDomainMapper> { WeatherForecastToDomainMapperImpl() }
    factory<CityDomainToDataMapper> { CityDomainToDataMapperImpl() }
    factory<CityDomainFromWeatherCurrentMapper> { CityDomainFromWeatherCurrentMapperImpl() }

    factory<WeatherApi> {
        WeatherApiImpl(
            okHttpClient = get(),
            requestFactory = get(),
            weatherCurrentMapper = get(),
            weatherForecastMapper = get()
        )
    }

    factory<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherApi = get(),
            pref = get(),
            cityDomainFromWeatherCurrentMapper = get(),
            forecastMapper = get()
        )
    }

    factory<CityRepository> {
        CityRepositoryImpl(
            cityDao = get(),
            cityDomainToDataMapper = get(),
            cityToDomainMapper = get()
        )
    }
}

val domainModule = module {
    factory<ForecastWeatherUseCase> { ForecastWeatherUseCaseImpl(weatherRepository = get()) }
    factory<CheckCurrentWeatherForCityUseCase> {
        CheckCurrentWeatherForCityUseCaseImpl(
            weatherRepository = get()
        )
    }
    factory<CityListUseCase> { CityListUseCaseImpl(cityRepository = get()) }
    factory<CityAddUseCase> { CityAddUseCaseImpl(cityRepository = get()) }
    factory<CityAsActiveUseCase> { CityAsActiveUseCaseImpl(cityRepository = get()) }
    factory<CityActiveUseCase> { CityActiveUseCaseImpl(cityRepository = get()) }
}

val presentationModule = module {
    factory<WeatherForecastDomainToUiMapper> { WeatherForecastDomainToUiMapperImpl() }
    factory<CityDomainToUiMapper> { CityDomainToUiMapperImpl() }
    factory<CityUiToDomainMapper> { CityUiToDomainMapperImpl() }
    factory<CityToDomainMapper> { CityToDomainMapperImpl() }

    single<WeatherContract.Presenter> {
        WeatherPresenter(
            useCase = get(),
            cityActiveUseCase = get(),
            cityDomainToUiMapper = get(),
            forecastDomainToUiMapper = get()
        )
    }
    single<CityContract.Presenter> {
        CityPresenter(
            cityListUseCase = get(),
            cityAsActiveUseCase = get(),
            cityUiToDomainMapper = get(),
            cityDomainToUiMapper = get()
        )
    }

    single<AddCityContract.Presenter> {
        AddCityPresenter(
            cityAddUseCase = get(),
            currentWeatherUseCase = get()
        )
    }
}