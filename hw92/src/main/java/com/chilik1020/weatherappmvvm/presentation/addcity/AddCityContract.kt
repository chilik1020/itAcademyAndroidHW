package com.chilik1020.weatherappmvvm.presentation.addcity

import com.chilik1020.weatherappmvvm.presentation.MvpPresenter
import com.chilik1020.weatherappmvvm.presentation.MvpView

interface AddCityContract {

    interface View : MvpView {
        fun render(state: AddCityViewState)
    }

    interface Presenter : MvpPresenter<View> {
        fun fetchWeatherForCityName(name: String)
    }
}