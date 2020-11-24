package com.chilik1020.hw8.model

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.util.LOG_TAG_APP
import java.util.concurrent.Executors

class ContactRepositoryHandlerImpl(private  val contactDao: ContactDao) : ContactRepository {

    private val executor = Executors.newSingleThreadExecutor()

    override fun getAllContacts(): List<Contact> {
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