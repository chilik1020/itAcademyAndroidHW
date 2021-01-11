package com.chilik1020.weatherappmvvm.presentation.models

data class CityUiModel(
    val name: String,
    val lat: String,
    val lon: String,
    val isCurrentCity: Boolean
)