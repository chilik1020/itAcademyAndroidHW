package com.chilik1020.weatherappmvvm.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.chilik1020.weatherappmvvm.data.entities.CityDomainFromWeatherCurrentMapperImpl
import com.chilik1020.weatherappmvvm.data.entities.CityDomainToDataMapper
import com.chilik1020.weatherappmvvm.data.entities.CityDomainToDataMapperImpl
import com.chilik1020.weatherappmvvm.data.entities.CityToDomainMapperImpl
import com.chilik1020.weatherappmvvm.data.entities.JsonToWeatherCurrentMapper
import com.chilik1020.weatherappmvvm.data.entities.JsonToWeatherForecastMapper
import com.chilik1020.weatherappmvvm.data.entities.WeatherForecastToDomainMapperImpl
import com.chilik1020.weatherappmvvm.data.local.AppDatabase
import com.chilik1020.weatherappmvvm.data.remote.RequestFactory
import com.chilik1020.weatherappmvvm.data.remote.RequestFactoryImpl
import com.chilik1020.weatherappmvvm.data.remote.WeatherApi
import com.chilik1020.weatherappmvvm.data.remote.WeatherApiImpl
import com.chilik1020.weatherappmvvm.data.repositories.CityRepository
import com.chilik1020.weatherappmvvm.data.repositories.CityRepositoryImpl
import com.chilik1020.weatherappmvvm.data.repositories.WeatherRepository
import com.chilik1020.weatherappmvvm.data.repositories.WeatherRepositoryImpl
import com.chilik1020.weatherappmvvm.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvvm.domain.CheckCurrentWeatherForCityUseCaseImpl
import com.chilik1020.weatherappmvvm.domain.CityActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityActiveUseCaseImpl
import com.chilik1020.weatherappmvvm.domain.CityAddUseCase
import com.chilik1020.weatherappmvvm.domain.CityAddUseCaseImpl
import com.chilik1020.weatherappmvvm.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityAsActiveUseCaseImpl
import com.chilik1020.weatherappmvvm.domain.CityListUseCase
import com.chilik1020.weatherappmvvm.domain.CityListUseCaseImpl
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCaseImpl
import com.chilik1020.weatherappmvvm.domain.models.CityDomainFromWeatherCurrentMapper
import com.chilik1020.weatherappmvvm.domain.models.CityToDomainMapper
import com.chilik1020.weatherappmvvm.domain.models.WeatherForecastToDomainMapper
import com.chilik1020.weatherappmvvm.presentation.addcity.AddCityContract
import com.chilik1020.weatherappmvvm.presentation.addcity.AddCityPresenter
import com.chilik1020.weatherappmvvm.presentation.addcity.AddCityViewModel
import com.chilik1020.weatherappmvvm.presentation.city.CityContract
import com.chilik1020.weatherappmvvm.presentation.city.CityPresenter
import com.chilik1020.weatherappmvvm.presentation.city.CityViewModel
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapperImpl
import com.chilik1020.weatherappmvvm.presentation.models.CityUiToDomainMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityUiToDomainMapperImpl
import com.chilik1020.weatherappmvvm.presentation.models.WeatherForecastDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.WeatherForecastDomainToUiMapperImpl
import com.chilik1020.weatherappmvvm.presentation.weather.WeatherContract
import com.chilik1020.weatherappmvvm.presentation.weather.WeatherPresenter
import com.chilik1020.weatherappmvvm.presentation.weather.WeatherViewModel
import com.chilik1020.weatherappmvvm.utils.DATABASE_NAME
import com.chilik1020.weatherappmvvm.utils.SHARED_PREF_NAME
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
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
    viewModel {
        WeatherViewModel(
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

    viewModel {
        CityViewModel(
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

    viewModel {
        AddCityViewModel(
            cityAddUseCase = get(),
            currentWeatherUseCase = get()
        )
    }
}