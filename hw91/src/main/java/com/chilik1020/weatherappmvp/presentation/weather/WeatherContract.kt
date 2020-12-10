package com.chilik1020.weatherappmvp.presentation.weather

import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject
import com.chilik1020.weatherappmvp.presentation.MvpPresenter
import com.chilik1020.weatherappmvp.presentation.MvpView

interface WeatherContract {

    interface View : MvpView {
        fun setData(data: WeatherForecastTopObject)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData()
    }
}