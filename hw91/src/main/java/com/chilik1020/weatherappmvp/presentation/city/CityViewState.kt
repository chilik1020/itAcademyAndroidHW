package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.data.entities.City

sealed class CityViewState {
    object Loading : CityViewState()
    class Loaded(val data: List<City>) : CityViewState()
    class Error(val error: String) : CityViewState()
}