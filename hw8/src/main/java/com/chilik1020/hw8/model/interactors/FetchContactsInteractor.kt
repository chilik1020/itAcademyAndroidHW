package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.Result
import com.chilik1020.hw8.model.repositories.ContactRepository

interface FetchContactsInteractor {

    fun interface OnFetchContactsListener {
        fun onFinish(result: Result<List<Contact>>)
    }

    fun fetchData(
        repository: ContactRepository,
        listener: OnFetchContactsListener
    )
}