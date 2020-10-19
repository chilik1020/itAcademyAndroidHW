package com.chilik1020.hw41.model

import com.chilik1020.hw41.model.entities.Contact

interface ContactRepository {
    fun getAllContacts(): List<Contact>
    fun getById(id: Int): Contact
    fun getLargestId(): Int
    fun addContact(contact: Contact)
    fun editContact(contact: Contact)
    fun removeContact(id: Int)
}