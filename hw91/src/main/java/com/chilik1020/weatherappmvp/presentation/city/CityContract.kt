package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.presentation.MvpPresenter
import com.chilik1020.weatherappmvp.presentation.MvpView

interface CityContract {

    interface View : MvpView {
        fun render(state: CityViewState)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadCities()
        fun addCity()
    }

}