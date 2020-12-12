package com.chilik1020.weatherappmvp.presentation.addcity

import android.util.Log
import com.chilik1020.weatherappmvp.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvp.domain.CityAddUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.utils.LOG_TAG

class AddCityPresenter(
    private val cityAddUseCase: CityAddUseCase,
    private val currentWeatherUseCase: CheckCurrentWeatherForCityUseCase
) : AddCityContract.Presenter {

    private var view: AddCityContract.View? = null

    private val addCityListener = CityAddUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                view?.render(AddCityViewState.Loaded)
            }
            is Result.Failure -> {
                view?.render(AddCityViewState.Error("Error while saving city"))
                Log.d(LOG_TAG, result.error.toString())
            }
        }
    }

    private val currentWeatherListener = CheckCurrentWeatherForCityUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                cityAddUseCase.addCity(result.data, addCityListener)
            }
            is Result.Failure -> {
                view?.render(AddCityViewState.Error("City not found"))
            }
        }
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