package com.chilik1020.weatherappmvvm.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chilik1020.weatherappmvvm.domain.CityActiveUseCase
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel
import com.chilik1020.weatherappmvvm.presentation.models.WeatherForecastDomainToUiMapper

class WeatherViewModel(
    private val useCase: ForecastWeatherUseCase,
    private val cityActiveUseCase: CityActiveUseCase,
    private val cityDomainToUiMapper: CityDomainToUiMapper,
    private val forecastDomainToUiMapper: WeatherForecastDomainToUiMapper
) : ViewModel() {

    private val mutableViewState: MutableLiveData<WeatherForecastViewState> =
        MutableLiveData(WeatherForecastViewState.Loading)
    val viewState: LiveData<WeatherForecastViewState>
        get() = mutableViewState

    private lateinit var activeCity: CityUiModel

    fun loadData() {
        mutableViewState.value = WeatherForecastViewState.Loading
        val subscribe = cityActiveUseCase.getActiveCity()
            .subscribe(
                {
                    activeCity = cityDomainToUiMapper.map(it)
                    loadForecast(it.lat, it.lon)
                },
                {
                    mutableViewState.value = WeatherForecastViewState.Error("Choose city")
                }
            )
    }

    private fun loadForecast(lat: String, lon: String) {
        val subscribe = useCase.getHourlyForecast(lat = lat, lon = lon)
            .subscribe(
                {
                    mutableViewState.value = WeatherForecastViewState.Loaded(
                        forecastDomainToUiMapper.map(it),
                        activeCity
                    )
                },
                {
                    mutableViewState.value = WeatherForecastViewState.Error(it.message.toString())
                }
            )
    }
}