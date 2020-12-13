package com.chilik1020.weatherappmvvm.presentation

interface MvpView
interface MvpPresenter<V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}