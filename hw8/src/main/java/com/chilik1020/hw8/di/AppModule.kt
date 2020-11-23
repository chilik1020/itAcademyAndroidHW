package com.chilik1020.hw8.di

import android.app.Application
import androidx.room.Room
import com.chilik1020.hw8.model.AppDatabase
import com.chilik1020.hw8.model.ContactRepository
import com.chilik1020.hw8.model.ContactRepositoryCompletableFutureImpl
import com.chilik1020.hw8.model.ContactRepositoryRxJavaImpl
import com.chilik1020.hw8.util.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    fun provideContactsDao(database: AppDatabase) = database.contactDao

    single { provideDatabase(androidApplication()) }
    single { provideContactsDao(get()) }
}

val repositoryModule = module {
    factory<ContactRepository> { ContactRepositoryCompletableFutureImpl(contactDao = get()) }
//    factory<ContactRepository> { ContactRepositoryRxJavaImpl(contactDao = get()) }
}