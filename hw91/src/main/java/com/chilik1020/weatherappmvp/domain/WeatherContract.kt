package com.chilik1020.weatherappmvp.domain

import com.chilik1020.weatherappmvp.data.entities.WeatherForecastTopObject

interface WeatherContract {

    interface View : MvpView {
        fun setData(data: WeatherForecastTopObject)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData()
    }
}