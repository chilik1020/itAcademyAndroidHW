package com.chilik1020.hw8.model

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.repositories.ContactRepository

interface FetchContactsInteractor {

    interface OnFetchContactsListener {
        fun onSuccess(data: List<Contact>)
        fun onError()
    }

    fun fetchData(
        repository: ContactRepository,
        listener: FetchContactsInteractor.OnFetchContactsListener
    )
}