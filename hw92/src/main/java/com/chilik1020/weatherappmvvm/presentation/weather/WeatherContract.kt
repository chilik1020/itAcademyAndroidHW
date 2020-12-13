package com.chilik1020.weatherappmvvm.presentation.weather

import com.chilik1020.weatherappmvvm.presentation.MvpPresenter
import com.chilik1020.weatherappmvvm.presentation.MvpView

interface WeatherContract {

    interface View : MvpView {
        fun render(state: WeatherForecastViewState)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData()
    }
}