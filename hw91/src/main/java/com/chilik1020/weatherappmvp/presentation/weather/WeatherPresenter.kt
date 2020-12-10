package com.chilik1020.weatherappmvp.presentation.weather

import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvp.domain.Result

class WeatherPresenter(private val useCase: ForecastWeatherUseCase) :
    WeatherContract.Presenter, ForecastWeatherUseCase.OnFinished {

    private var view: WeatherContract.View? = null

    override fun loadData() {
        view?.render(WeatherForecastViewState.Loading)
        useCase.getHourlyForecast(lat = "53.9", lon = "20.0", listener = this)
    }

    override fun attachView(view: WeatherContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onResponse(data: Result<WeatherForecastTopObject>) {
        when (data) {
            is Result.Success -> {
                view?.render(WeatherForecastViewState.Loaded(data.data))
            }
            is Result.Failure -> {
                view?.render(WeatherForecastViewState.Error("Error"))
            }
        }
    }
}