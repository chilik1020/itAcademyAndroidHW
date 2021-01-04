package com.chilik1020.hw11.domain

import com.chilik1020.hw11.data.Contact
import com.chilik1020.hw11.data.ContactRepository
import com.chilik1020.hw11.data.Result

interface FetchContactsInteractor {

    fun interface OnFetchContactsListener {
        fun onFinish(result: Result<List<Contact>>)
    }

    fun fetchData(
        repository: ContactRepository,
        listener: OnFetchContactsListener
    )
}