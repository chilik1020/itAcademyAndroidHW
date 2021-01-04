package com.chilik1020.hw11.di

import com.chilik1020.hw11.data.repositories.ContactRepository
import com.chilik1020.hw11.data.repositories.ContactRepositoryImpl
import com.chilik1020.hw11.data.sources.ContactSourceContentResolverImpl
import com.chilik1020.hw11.data.sources.ContactsSource
import com.chilik1020.hw11.domain.FetchContactsInteractor
import com.chilik1020.hw11.domain.FetchContactsInteractorImpl
import com.chilik1020.hw11.presentation.ContactsListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<ContactsSource> { ContactSourceContentResolverImpl(androidApplication().contentResolver) }
    factory<ContactRepository> { ContactRepositoryImpl(get()) }
    factory<FetchContactsInteractor> { FetchContactsInteractorImpl() }
    viewModel { ContactsListViewModel(repository = get(), interactor = get()) }
}
