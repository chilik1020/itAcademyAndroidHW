package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.repositories.ContactRepository

class EditContactInteractorImpl : EditContactInteractor {
    override fun fetchContactById(
        id: String,
        repository: ContactRepository,
        listener: EditContactInteractor.OnFetchContactByIdListener
    ) {
        repository.getById(id, listener)
    }

    override fun editContact(contact: Contact, repository: ContactRepository) {
        repository.editContact(contact)
    }

    override fun deleteContact(id: String, repository: ContactRepository) {
        repository.removeContact(id)
    }
}