package com.chilik1020.hw11.data

import com.chilik1020.hw11.domain.FetchContactsInteractor

interface ContactRepository {
    fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener)
}