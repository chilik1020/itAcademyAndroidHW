package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.Result
import com.chilik1020.hw8.model.repositories.ContactRepository

interface CreateContactInteractor {

    fun interface OnCreateContactListener {
        fun onFinish(result: Result<Long>)
    }

    fun createContact(
        contact: Contact,
        repository: ContactRepository,
        listener: OnCreateContactListener
    )
}