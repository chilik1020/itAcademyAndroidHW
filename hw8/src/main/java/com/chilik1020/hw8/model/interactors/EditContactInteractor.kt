package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.Result
import com.chilik1020.hw8.model.repositories.ContactRepository

interface EditContactInteractor {

    fun interface OnFetchContactByIdListener {
        fun onFinish(result: Result<Contact>)
    }

    fun fetchContactById(
        id: String,
        repository: ContactRepository,
        listener: OnFetchContactByIdListener
    )

    fun editContact(contact: Contact, repository: ContactRepository)
    fun deleteContact(id: String, repository: ContactRepository)
}