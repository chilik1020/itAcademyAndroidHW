package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.CityListUseCase
import com.chilik1020.weatherappmvp.domain.CurrentWeatherUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvp.presentation.models.CityUiModel
import com.chilik1020.weatherappmvp.presentation.models.CityUiToDomainMapper

class CityPresenter(
    private val cityListUseCase: CityListUseCase,
    private val cityAddUseCase: CityAddUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val cityDomainToUiMapper: CityDomainToUiMapper,
    private val cityUiToDomainMapper: CityUiToDomainMapper
) : CityContract.Presenter {

    private var view: CityContract.View? = null

    private val loadCitiesListener = CityListUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                val list =
                    result.data.map { cityDomain -> cityDomainToUiMapper.map(cityDomain) }.toList()
                view?.render(CityViewState.Loaded(list))
            }
            is Result.Failure -> {

            }
        }
    }

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

    override fun loadCities() {
        cityListUseCase.getCities(loadCitiesListener)
    }

    override fun addCity(city: CityUiModel) {
        cityAddUseCase.addCity(cityUiToDomainMapper.map(city), addCityListener)
    }

    override fun fetchWeatherForCityName(name: String) {
        currentWeatherUseCase.getCurrentWeather(name, currentWeatherListener)
    }

    override fun attachView(view: CityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}