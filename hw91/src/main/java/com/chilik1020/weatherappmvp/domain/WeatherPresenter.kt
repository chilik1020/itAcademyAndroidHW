package com.chilik1020.weatherappmvp.domain

import android.util.Log
import com.chilik1020.weatherappmvp.data.remote.WeatherApiImpl
import com.chilik1020.weatherappmvp.utils.LOG_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class WeatherPresenter : WeatherContract.Presenter {

    private var view: WeatherContract.View? = null
    private val weatherApi = WeatherApiImpl()
    private lateinit var subscribe: Disposable

    override fun loadData() {
        subscribe = weatherApi.getHourlyForecastWeather()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view?.setData(it) },
                { Log.d(LOG_TAG, it.message.toString()) }
            )
    }

    override fun attachView(view: WeatherContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}