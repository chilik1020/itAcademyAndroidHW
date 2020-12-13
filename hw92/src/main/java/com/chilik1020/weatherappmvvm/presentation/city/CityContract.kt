package com.chilik1020.weatherappmvvm.presentation.city

import com.chilik1020.weatherappmvvm.presentation.MvpPresenter
import com.chilik1020.weatherappmvvm.presentation.MvpView
import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel

interface CityContract {

    interface View : MvpView {
        fun render(state: CityViewState)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadCities()
        fun setCityAsActive(city: CityUiModel)
    }

}