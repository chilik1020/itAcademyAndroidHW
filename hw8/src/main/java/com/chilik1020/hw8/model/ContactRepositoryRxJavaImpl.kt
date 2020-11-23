package com.chilik1020.hw8.model

import com.chilik1020.hw8.model.entities.Contact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactRepositoryRxJavaImpl(private val contactDao: ContactDao) : ContactRepository {

    override fun getAllContacts(): List<Contact> {
        return contactDao.getAllRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .blockingFirst()
    }

    override fun getById(id: String): Contact {
        return contactDao.getByIdRx(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .blockingFirst()
    }

    override fun addContact(contact: Contact) {
        contactDao.addRx(contact)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun editContact(contact: Contact) {
        contactDao.editRx(contact)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun removeContact(id: String) {
        contactDao.deleteRx(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}