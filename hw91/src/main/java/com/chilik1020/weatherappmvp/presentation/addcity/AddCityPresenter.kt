package com.chilik1020.weatherappmvp.presentation.addcity

import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.CurrentWeatherUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.presentation.models.CityUiModel
import com.chilik1020.weatherappmvp.presentation.models.CityUiToDomainMapper

class AddCityPresenter(
    private val cityAddUseCase: CityAddUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val cityUiToDomainMapper: CityUiToDomainMapper
) : AddCityContract.Presenter {

    private var view: AddCityContract.View? = null

    private val addCityListener = CityAddUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {

            }
            is Result.Failure -> {

            }
        }
    }

    private val currentWeatherListener = CurrentWeatherUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
            }
            is Result.Failure -> {
            }
        }
    }

    override fun addCity(city: CityUiModel) {
        cityAddUseCase.addCity(cityUiToDomainMapper.map(city), addCityListener)
    }

    override fun fetchWeatherForCityName(name: String) {
        currentWeatherUseCase.getCurrentWeather(name, currentWeatherListener)
    }

    override fun attachView(view: AddCityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}