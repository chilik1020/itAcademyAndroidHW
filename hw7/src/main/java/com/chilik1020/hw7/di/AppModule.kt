package com.chilik1020.hw7.di

import com.chilik1020.hw7.model.ContactRepository
import com.chilik1020.hw7.model.ContactRepositoryAppClassImpl
import com.chilik1020.hw7.model.SimpleDataStorage
import org.koin.dsl.module

val repositoryModule = module {
    factory<ContactRepository> { ContactRepositoryAppClassImpl(SimpleDataStorage) }
}