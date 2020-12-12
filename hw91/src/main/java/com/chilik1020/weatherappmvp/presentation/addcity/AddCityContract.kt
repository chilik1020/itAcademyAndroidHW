package com.chilik1020.weatherappmvp.presentation.addcity

import com.chilik1020.weatherappmvp.presentation.MvpPresenter
import com.chilik1020.weatherappmvp.presentation.MvpView
import com.chilik1020.weatherappmvp.presentation.models.CityUiModel

interface AddCityContract {

    interface View : MvpView {
        fun render(state: AddCityViewState)
    }

    interface Presenter : MvpPresenter<View> {
        fun fetchWeatherForCityName(name: String)
    }
}