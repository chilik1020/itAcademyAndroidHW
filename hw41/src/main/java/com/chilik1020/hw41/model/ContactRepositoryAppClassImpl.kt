package com.chilik1020.hw41.model

import com.chilik1020.hw41.ContactApp
import com.chilik1020.hw41.model.entities.Contact

class ContactRepositoryAppClassImpl : ContactRepository {
    override fun getAllContacts(): List<Contact> {
        return ContactApp.contacts
    }

    override fun getById(id: Int): Contact {
        return ContactApp.contacts.first { contact: Contact -> contact.id == id }
    }

    override fun getLargestId(): Int {
        return ContactApp.contacts.map { c: Contact -> c.id }.max() ?: 0
    }

    override fun addContact(contact: Contact) {
        ContactApp.contacts.add(contact)
    }

    override fun editContact(contact: Contact) {
        val c = ContactApp.contacts.find { c: Contact -> c.id == contact.id }
        c?.apply {
            fullname = contact.fullname
            number = contact.number
            email = contact.email
        }
    }

    override fun removeContact(id: Int) {
        val contact = ContactApp.contacts.find { it.id == id }
        if (contact != null) {
            ContactApp.contacts.remove(contact)
        }
    }
}