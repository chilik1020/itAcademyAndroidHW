package com.chilik1020.hw11.domain

import com.chilik1020.hw11.data.repositories.ContactRepository

class FetchContactsInteractorImpl : FetchContactsInteractor {

    override fun fetchData(
        repository: ContactRepository,
        listener: FetchContactsInteractor.OnFetchContactsListener
    ) {
        repository.getAllContacts(listener)
    }
}