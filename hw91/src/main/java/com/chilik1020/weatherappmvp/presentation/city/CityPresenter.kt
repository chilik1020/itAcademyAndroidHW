package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.data.entities.WeatherCurrentTopObject
import com.chilik1020.weatherappmvp.domain.CurrentWeatherUseCase
import com.chilik1020.weatherappmvp.domain.Result

class CityPresenter(
    private val currentWeatherUseCase: CurrentWeatherUseCase
) : CityContract.Presenter, CurrentWeatherUseCase.OnFinished {

    private var view: CityContract.View? = null

    private val addCityListener = CurrentWeatherUseCase.OnFinished {
        when (it) {
            is Result.Success -> {}
            is Result.Failure -> {}
        }
    }

    override fun loadCities() {
        currentWeatherUseCase.getCurrentWeather("Minsk", this)
    }

    override fun addCity() {
        TODO("Not yet implemented")
    }

    override fun attachView(view: CityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onResponse(result: Result<WeatherCurrentTopObject>) {


    }
}