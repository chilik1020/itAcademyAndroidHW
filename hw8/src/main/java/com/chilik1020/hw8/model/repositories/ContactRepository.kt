package com.chilik1020.hw8.model.repositories

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.interactors.CreateContactInteractor
import com.chilik1020.hw8.model.interactors.EditContactInteractor
import com.chilik1020.hw8.model.interactors.FetchContactsInteractor

interface ContactRepository {
    fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener)
    fun getById(id: String, listener: EditContactInteractor.OnFetchContactByIdListener)
    fun addContact(contact: Contact, listener: CreateContactInteractor.OnCreateContactListener)
    fun editContact(contact: Contact)
    fun removeContact(id: String)
}