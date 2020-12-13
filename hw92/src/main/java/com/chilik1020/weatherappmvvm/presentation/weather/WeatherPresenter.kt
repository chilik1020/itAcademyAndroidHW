package com.chilik1020.weatherappmvvm.presentation.weather

import android.util.Log
import com.chilik1020.weatherappmvvm.domain.CityActiveUseCase
import com.chilik1020.weatherappmvvm.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvvm.domain.Result
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel
import com.chilik1020.weatherappmvvm.presentation.models.WeatherForecastDomainToUiMapper
import com.chilik1020.weatherappmvvm.utils.LOG_TAG

class WeatherPresenter(
    private val useCase: ForecastWeatherUseCase,
    private val cityActiveUseCase: CityActiveUseCase,
    private val cityDomainToUiMapper: CityDomainToUiMapper,
    private val forecastDomainToUiMapper: WeatherForecastDomainToUiMapper
) :
    WeatherContract.Presenter {

    private var view: WeatherContract.View? = null
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
                view?.render(WeatherForecastViewState.Error("Choose city"))
                Log.d(LOG_TAG, result.error.toString())
            }
        }
    }

    private val loadCitiesListener = ForecastWeatherUseCase.OnFinished {
        when (it) {
            is Result.Success -> {
                view?.render(
                    WeatherForecastViewState.Loaded(
                        forecastDomainToUiMapper.map(it.data),
                        activeCity
                    )
                )
            }
            is Result.Failure -> {
                view?.render(WeatherForecastViewState.Error("Error"))
            }
        }
    }

    override fun loadData() {
        view?.render(WeatherForecastViewState.Loading)
        cityActiveUseCase.getActiveCity(getActiveCityListener)
    }

    override fun attachView(view: WeatherContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}