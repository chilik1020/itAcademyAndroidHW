package com.chilik1020.weatherappmvvm.presentation.addcity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chilik1020.weatherappmvvm.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvvm.domain.CityAddUseCase
import com.chilik1020.weatherappmvvm.domain.Result
import com.chilik1020.weatherappmvvm.utils.LOG_TAG

class AddCityViewModel(
    private val cityAddUseCase: CityAddUseCase,
    private val currentWeatherUseCase: CheckCurrentWeatherForCityUseCase
) : ViewModel() {

    private val mutableViewState: MutableLiveData<AddCityViewState> =
        MutableLiveData(AddCityViewState.Loading)
    val viewState: LiveData<AddCityViewState>
        get() = mutableViewState

    private val addCityListener = CityAddUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                mutableViewState.value = AddCityViewState.Loaded
            }
            is Result.Failure -> {
                mutableViewState.value = AddCityViewState.Error("Error while saving city")
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
                mutableViewState.value = AddCityViewState.Error("City not found")
            }
        }
    }

    fun fetchWeatherForCityName(name: String) {
        mutableViewState.value = AddCityViewState.Loading
        currentWeatherUseCase.getCurrentWeather(name, currentWeatherListener)
    }
}