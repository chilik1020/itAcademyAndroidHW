package com.chilik1020.hw8

import android.app.Application
import android.content.Context
import com.chilik1020.hw8.di.appModule
import com.chilik1020.hw8.di.interactorModule
import com.chilik1020.hw8.di.roomModule
import com.chilik1020.hw8.di.viewModelModule
import com.chilik1020.hw8.util.REPOSITORY_TYPE_KEY
import com.chilik1020.hw8.util.SHARED_PREF_NAME
import com.chilik1020.hw8.util.TYPE_RX_JAVA
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ContactApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()

        applicationContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(REPOSITORY_TYPE_KEY, TYPE_RX_JAVA)
            .apply()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ContactApp)
            modules(appModule, roomModule, interactorModule, viewModelModule)
        }
    }
}