package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.repositories.ContactRepository

interface CreateContactInteractor {

    interface OnCreateContactListener {
        fun onSuccess()
        fun onError()
    }

    fun createContact(
        contact: Contact,
        repository: ContactRepository,
        listener: OnCreateContactListener
    )
}