package com.chilik1020.hw11.domain

import com.chilik1020.hw11.data.entities.Contact
import com.chilik1020.hw11.data.repositories.ContactRepository
import com.chilik1020.hw11.data.entities.Result

interface FetchContactsInteractor {

    fun interface OnFetchContactsListener {
        fun onFinish(result: Result<List<Contact>>)
    }

    fun fetchData(
        repository: ContactRepository,
        listener: OnFetchContactsListener
    )
}