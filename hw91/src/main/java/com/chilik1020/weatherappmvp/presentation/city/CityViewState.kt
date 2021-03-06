package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.presentation.models.CityUiModel

sealed class CityViewState {
    object Loading : CityViewState()
    class Loaded(val data: List<CityUiModel>) : CityViewState()
    class Error(val error: String) : CityViewState()
}