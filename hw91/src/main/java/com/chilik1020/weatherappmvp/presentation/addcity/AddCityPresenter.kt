package com.chilik1020.weatherappmvp.presentation.addcity

import com.chilik1020.weatherappmvp.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class AddCityPresenter(
    private val cityAddUseCase: CityAddUseCase,
    private val currentWeatherUseCase: CheckCurrentWeatherForCityUseCase
) : AddCityContract.Presenter {

    private var view: AddCityContract.View? = null

    private val currentWeatherListener = CheckCurrentWeatherForCityUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                addCity(result.data)
            }
            is Result.Failure -> {
                view?.render(AddCityViewState.Error("City not found"))
            }
        }
    }

    override fun fetchWeatherForCityName(name: String) {
        currentWeatherUseCase.getCurrentWeather(name, currentWeatherListener)
    }

    private fun addCity(city: CityDomainModel) {
        val subscribe = cityAddUseCase.addCity(city)
            .subscribe(
                {
                    view?.render(AddCityViewState.Loaded)
                },
                {
                    view?.render(AddCityViewState.Error(it.message.toString()))
                }
            )
    }

    override fun attachView(view: AddCityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}