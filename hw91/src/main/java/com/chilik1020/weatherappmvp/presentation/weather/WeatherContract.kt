package com.chilik1020.weatherappmvp.presentation.weather

import com.chilik1020.weatherappmvp.presentation.MvpPresenter
import com.chilik1020.weatherappmvp.presentation.MvpView

interface WeatherContract {

    interface View : MvpView {
        fun render(state: WeatherForecastViewState)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData()
    }
}