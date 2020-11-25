package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.repositories.ContactRepository

class FetchContactsInteractorImpl : FetchContactsInteractor {

    override fun fetchData(
        repository: ContactRepository,
        listener: FetchContactsInteractor.OnFetchContactsListener
    ) {
        repository.getAllContacts(listener)
    }
}