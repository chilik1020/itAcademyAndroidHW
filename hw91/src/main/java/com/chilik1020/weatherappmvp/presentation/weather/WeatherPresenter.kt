package com.chilik1020.weatherappmvp.presentation.weather

import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.presentation.models.WeatherForecastDomainToUiMapper

class WeatherPresenter(
    private val useCase: ForecastWeatherUseCase,
    private val forecastToUiMapper: WeatherForecastDomainToUiMapper
) :
    WeatherContract.Presenter {

    private var view: WeatherContract.View? = null

    private val listener = ForecastWeatherUseCase.OnFinished {
        when (it) {
            is Result.Success -> {
                view?.render(WeatherForecastViewState.Loaded(forecastToUiMapper.map(it.data)))
            }
            is Result.Failure -> {
                view?.render(WeatherForecastViewState.Error("Error"))
            }
        }
    }

    override fun loadData() {
        view?.render(WeatherForecastViewState.Loading)
        useCase.getHourlyForecast(lat = "53.9", lon = "20.0", listener = listener)
    }

    override fun attachView(view: WeatherContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}