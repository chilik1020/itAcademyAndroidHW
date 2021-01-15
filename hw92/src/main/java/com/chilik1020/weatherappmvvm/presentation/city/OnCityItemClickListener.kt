package com.chilik1020.weatherappmvvm.presentation.city

import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel

fun interface OnCityItemClickListener {
    fun onClick(city: CityUiModel)
}