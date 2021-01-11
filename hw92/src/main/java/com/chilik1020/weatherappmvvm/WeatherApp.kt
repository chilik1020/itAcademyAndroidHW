package com.chilik1020.weatherappmvvm

import android.app.Application
import com.chilik1020.weatherappmvvm.di.appModule
import com.chilik1020.weatherappmvvm.di.dataModule
import com.chilik1020.weatherappmvvm.di.domainModule
import com.chilik1020.weatherappmvvm.di.presentationModule
import com.chilik1020.weatherappmvvm.di.roomModule
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
            modules(appModule, roomModule, domainModule, dataModule, presentationModule)
        }
    }
}