package com.chilik1020.hw41.model

import com.chilik1020.hw41.ContactApp
import com.chilik1020.hw41.model.entities.Contact
import java.util.*

class ContactRepositoryAppClassImpl : ContactRepository {
    override fun getAllContacts(): List<Contact> {
        return ContactApp.contacts
    }

    override fun getById(id: UUID): Contact {
        return ContactApp.contacts.first { it.id == id }
    }

    override fun addContact(contact: Contact) {
        ContactApp.contacts.add(contact)
    }

    override fun editContact(contact: Contact) {
        val c = ContactApp.contacts.find {it.id == contact.id }
        c?.apply {
            fullname = contact.fullname
            number = contact.number
            email = contact.email
        }
    }

    override fun removeContact(id: UUID) {
        val contact = ContactApp.contacts.find { it.id == id }
        if (contact != null) {
            ContactApp.contacts.remove(contact)
        }
    }
}