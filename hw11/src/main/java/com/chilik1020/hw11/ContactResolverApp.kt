package com.chilik1020.hw11

import android.app.Application
import com.chilik1020.hw11.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ContactResolverApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ContactResolverApp)
            modules(appModule)
        }
    }
}