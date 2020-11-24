package com.chilik1020.hw8.model

import android.util.Log
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.util.LOG_TAG_APP
import io.reactivex.schedulers.Schedulers

class ContactRepositoryRxJavaImpl(private val contactDao: ContactDao) : ContactRepository {

    override fun getAllContacts(): List<Contact> {
        Log.d(LOG_TAG_APP, "RxJava: getAllContacts")
        return contactDao.getAllRx()
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }

    override fun getById(id: String): Contact {
        Log.d(LOG_TAG_APP, "RxJava: getById")
        return contactDao.getByIdRx(id)
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }

    override fun addContact(contact: Contact) {
        Log.d(LOG_TAG_APP, "RxJava: addContact")
        contactDao.addRx(contact)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun editContact(contact: Contact) {
        Log.d(LOG_TAG_APP, "RxJava: editContact")
        contactDao.editRx(contact)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun removeContact(id: String) {
        Log.d(LOG_TAG_APP, "RxJava: removeContact")
        contactDao.deleteRx(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}