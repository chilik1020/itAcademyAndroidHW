package com.chilik1020.weatherappmvvm.presentation.addcity

sealed class AddCityViewState {
    object Loading : AddCityViewState()
    object Loaded : AddCityViewState()
    class Error(val error: String) : AddCityViewState()
}