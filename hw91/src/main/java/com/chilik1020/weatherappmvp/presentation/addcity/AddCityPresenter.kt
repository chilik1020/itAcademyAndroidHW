package com.chilik1020.weatherappmvp.presentation.addcity

import com.chilik1020.weatherappmvp.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.models.CityDomainModel

class AddCityPresenter(
    private val cityAddUseCase: CityAddUseCase,
    private val currentWeatherUseCase: CheckCurrentWeatherForCityUseCase
) : AddCityContract.Presenter {

    private var view: AddCityContract.View? = null

    override fun fetchWeatherForCityName(name: String) {
        val subscribe = currentWeatherUseCase.getCityIfCurrentWeatherPresented(name)
            .subscribe(
                { addCity(it) },
                { view?.render(AddCityViewState.Error("City not found")) }
            )
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