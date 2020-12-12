package com.chilik1020.weatherappmvp.presentation.models

data class CityUiModel(
    val name: String,
    val lat: String,
    val lon: String,
    val isCurrentCity: Boolean
)