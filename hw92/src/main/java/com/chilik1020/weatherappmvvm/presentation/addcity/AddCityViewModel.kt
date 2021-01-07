package com.chilik1020.weatherappmvvm.presentation.addcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chilik1020.weatherappmvvm.domain.CheckCurrentWeatherForCityUseCase
import com.chilik1020.weatherappmvvm.domain.CityAddUseCase
import com.chilik1020.weatherappmvvm.domain.models.CityDomainModel

class AddCityViewModel(
    private val cityAddUseCase: CityAddUseCase,
    private val currentWeatherUseCase: CheckCurrentWeatherForCityUseCase
) : ViewModel() {

    private val mutableViewState: MutableLiveData<AddCityViewState> =
        MutableLiveData(AddCityViewState.Loading)
    val viewState: LiveData<AddCityViewState>
        get() = mutableViewState

    fun fetchWeatherForCityName(name: String) {
        mutableViewState.value = AddCityViewState.Loading
        val subscribe = currentWeatherUseCase.getCityIfCurrentWeatherPresented(name)
            .subscribe(
                { addCity(it) },
                { mutableViewState.value = AddCityViewState.Error("City not found") }
            )
    }

    private fun addCity(city: CityDomainModel) {
        val subscribe = cityAddUseCase.addCity(city)
            .subscribe(
                {
                    mutableViewState.value = AddCityViewState.Loaded
                },
                {
                    mutableViewState.value = AddCityViewState.Error("Error while saving city")
                }
            )
    }
}