package com.chilik1020.weatherappmvp

import android.app.Application
import com.chilik1020.weatherappmvp.di.appModule
import com.chilik1020.weatherappmvp.di.dataModule
import com.chilik1020.weatherappmvp.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            modules(appModule, dataModule, presenterModule)
        }
    }
}