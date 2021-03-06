package com.chilik1020.hw7

import android.app.Application
import com.chilik1020.hw7.di.repositoryModule
import com.chilik1020.hw7.di.roomModule
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
            modules(repositoryModule, roomModule)
        }
    }
}