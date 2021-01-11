package com.chilik1020.hw7.model

import com.chilik1020.hw7.model.entities.Contact

class ContactRepositoryRoomImpl(private val contactDao: ContactDao) : ContactRepository {

    override fun getAllContacts(): List<Contact> {
        return contactDao.getAll()
    }

    override fun getById(id: String): Contact {
        return contactDao.getById(id)
    }

    override fun addContact(contact: Contact) {
        contactDao.add(contact)
    }

    override fun editContact(contact: Contact) {
        contactDao.edit(contact)
    }

    override fun removeContact(id: String) {
        contactDao.delete(id)
    }

}