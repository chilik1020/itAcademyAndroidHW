package com.chilik1020.hw8.model.interactors

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.repositories.ContactRepository

class CreateContactInteractorImpl : CreateContactInteractor {
    override fun createContact(
        contact: Contact,
        repository: ContactRepository
    ) {
        repository.addContact(contact)
    }
}