package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.presentation.models.CityUiModel

fun interface OnCityItemClickListener {
    fun onClick(city:CityUiModel)
}