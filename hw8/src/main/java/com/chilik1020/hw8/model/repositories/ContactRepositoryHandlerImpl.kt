package com.chilik1020.hw8.model.repositories

import android.util.Log
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.model.FetchContactsInteractor
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.util.LOG_TAG_APP
import java.util.concurrent.Executors

class ContactRepositoryHandlerImpl(private  val contactDao: ContactDao) : ContactRepository {

    private val executor = Executors.newSingleThreadExecutor()

    override fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener) {
        Log.d(LOG_TAG_APP, "Handler: getAllContacts")
        TODO("Not yet implemented")
    }

    override fun getById(id: String): Contact {
        Log.d(LOG_TAG_APP, "Handler: getById")
        TODO("Not yet implemented")
    }

    override fun addContact(contact: Contact) {
        Log.d(LOG_TAG_APP, "Handler: addContact")
        executor.submit { contactDao.add(contact) }
    }

    override fun editContact(contact: Contact) {
        Log.d(LOG_TAG_APP, "Handler: editContact")
        executor.submit { contactDao.edit(contact) }
    }

    override fun removeContact(id: String) {
        Log.d(LOG_TAG_APP, "Handler: removeContact")
        executor.submit { contactDao.delete(id) }
    }
}