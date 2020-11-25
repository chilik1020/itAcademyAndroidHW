package com.chilik1020.hw8.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.chilik1020.hw8.model.interactors.CreateContactInteractor
import com.chilik1020.hw8.model.interactors.CreateContactInteractorImpl
import com.chilik1020.hw8.model.interactors.FetchContactsInteractor
import com.chilik1020.hw8.model.interactors.FetchContactsInteractorImpl
import com.chilik1020.hw8.model.local.AppDatabase
import com.chilik1020.hw8.util.DATABASE_NAME
import com.chilik1020.hw8.util.SHARED_PREF_NAME
import com.chilik1020.hw8.views.add.ContactAddViewModel
import com.chilik1020.hw8.views.edit.ContactEditViewModel
import com.chilik1020.hw8.views.list.ContactsListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
}

val roomModule = module {
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    fun provideContactsDao(database: AppDatabase) = database.contactDao

    single { provideDatabase(androidApplication()) }
    single { provideContactsDao(get()) }
}
val interactorModule = module {
    factory<FetchContactsInteractor> { FetchContactsInteractorImpl() }
    factory<CreateContactInteractor> { CreateContactInteractorImpl() }
}

val viewModelModule = module {
    viewModel { ContactsListViewModel(contactDao = get(), pref = get(), interactor = get()) }
    viewModel { ContactAddViewModel(contactDao = get(), pref = get(), interactor = get()) }
    viewModel { ContactEditViewModel(contactDao = get(), pref = get()) }
}