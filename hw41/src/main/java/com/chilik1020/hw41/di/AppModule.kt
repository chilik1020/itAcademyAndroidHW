package com.chilik1020.hw41.di

import com.chilik1020.hw41.model.ContactRepository
import com.chilik1020.hw41.model.ContactRepositoryAppClassImpl
import com.chilik1020.hw41.model.SimpleDataStorage
import org.koin.dsl.module

val repositoryModule = module {
    factory<ContactRepository> { ContactRepositoryAppClassImpl(SimpleDataStorage) }
}