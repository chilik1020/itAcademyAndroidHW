package com.chilik1020.hw8.model

import com.chilik1020.hw8.model.entities.Contact

interface ContactRepository {
    fun getAllContacts(): List<Contact>
    fun getById(id: String): Contact
    fun addContact(contact: Contact)
    fun editContact(contact: Contact)
    fun removeContact(id: String)
}