package com.chilik1020.hw8

import android.app.Application
import com.chilik1020.hw8.di.appModule
import com.chilik1020.hw8.di.interactorModule
import com.chilik1020.hw8.di.roomModule
import com.chilik1020.hw8.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ContactApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ContactApp)
            modules(appModule, roomModule, interactorModule, viewModelModule)
        }
    }
}