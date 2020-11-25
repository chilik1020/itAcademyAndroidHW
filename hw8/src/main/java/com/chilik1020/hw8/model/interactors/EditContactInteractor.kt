package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.repositories.ContactRepository

interface EditContactInteractor {

    interface OnFetchContactByIdListener {
        fun onSuccess(contact: Contact)
        fun onError()
    }

    fun fetchContactById(
        id: String,
        repository: ContactRepository,
        listener: OnFetchContactByIdListener
    )

    fun editContact(contact: Contact, repository: ContactRepository)
    fun deleteContact(id: String, repository: ContactRepository)
}