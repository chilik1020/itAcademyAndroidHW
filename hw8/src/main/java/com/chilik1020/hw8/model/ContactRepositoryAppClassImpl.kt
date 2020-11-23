package com.chilik1020.hw8.model

import com.chilik1020.hw8.model.entities.Contact

class ContactRepositoryAppClassImpl(private val storage: SimpleDataStorage) : ContactRepository {

    override fun getAllContacts(): List<Contact> {
        return storage.contacts
    }

    override fun getById(id: String): Contact {
        return storage.contacts.first { it.id == id }
    }

    override fun addContact(contact: Contact) {
        storage.contacts.add(contact)
    }

    override fun editContact(contact: Contact) {
        storage.contacts.find { it.id == contact.id }?.apply {
            fullname = contact.fullname
            contactInfo = contact.contactInfo
        }
    }

    override fun removeContact(id: String) {
        val contact = storage.contacts.find { it.id == id }
        contact?.let { storage.contacts.remove(it) }
    }
}