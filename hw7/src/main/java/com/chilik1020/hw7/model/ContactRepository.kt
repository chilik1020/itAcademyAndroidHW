package com.chilik1020.hw7.model

import com.chilik1020.hw7.model.entities.Contact
import java.util.*

interface ContactRepository {
    fun getAllContacts(): List<Contact>
    fun getById(id: UUID): Contact
    fun addContact(contact: Contact)
    fun editContact(contact: Contact)
    fun removeContact(id: UUID)
}