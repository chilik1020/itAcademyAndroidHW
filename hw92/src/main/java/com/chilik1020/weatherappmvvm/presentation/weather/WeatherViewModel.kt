package com.chilik1020.weatherappmvvm.presentation.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chilik1020.weatherappmvvm.domain.CityActiveUseCase
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvvm.domain.Result
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel
import com.chilik1020.weatherappmvvm.presentation.models.WeatherForecastDomainToUiMapper
import com.chilik1020.weatherappmvvm.utils.LOG_TAG

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

    private val getActiveCityListener = CityActiveUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                Log.d(LOG_TAG, result.data.toString())
                activeCity = cityDomainToUiMapper.map(result.data)
                useCase.getHourlyForecast(
                    lat = result.data.lat,
                    lon = result.data.lon,
                    listener = loadCitiesListener
                )
            }
            is Result.Failure -> {
                mutableViewState.value = WeatherForecastViewState.Error("Choose city")
                Log.d(LOG_TAG, result.error.toString())
            }
        }
    }

    private val loadCitiesListener = ForecastWeatherUseCase.OnFinished {
        when (it) {
            is Result.Success -> {
                mutableViewState.value = WeatherForecastViewState.Loaded(
                    forecastDomainToUiMapper.map(it.data),
                    activeCity
                )
            }
            is Result.Failure -> {
                mutableViewState.value = WeatherForecastViewState.Error("Error")
            }
        }
    }

    fun loadData() {
        mutableViewState.value = WeatherForecastViewState.Loading
        cityActiveUseCase.getActiveCity(getActiveCityListener)
    }
}