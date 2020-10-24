package com.chilik1020.hw41.model

import com.chilik1020.hw41.model.entities.Contact
import java.util.*

class ContactRepositoryAppClassImpl(private val data: MutableList<Contact>) : ContactRepository {

    override fun getAllContacts(): List<Contact> {
        return data
    }

    override fun getById(id: UUID): Contact {
        return data.first { it.id == id }
    }

    override fun addContact(contact: Contact) {
        data.add(contact)
    }

    override fun editContact(contact: Contact) {
        val c = data.find { it.id == contact.id }
        c?.apply {
            fullname = contact.fullname
            number = contact.number
            email = contact.email
        }
    }

    override fun removeContact(id: UUID) {
        val contact = data.find { it.id == id }
        contact?.let { data.remove(it) }
    }
}