package com.chilik1020.hw8.model.repositories

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.interactors.FetchContactsInteractor

interface ContactRepository {
    fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener)
    fun getById(id: String): Contact
    fun addContact(contact: Contact)
    fun editContact(contact: Contact)
    fun removeContact(id: String)
}