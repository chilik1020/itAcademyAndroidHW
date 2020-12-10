package com.chilik1020.weatherappmvp.domain

interface MvpView
interface MvpPresenter<V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}