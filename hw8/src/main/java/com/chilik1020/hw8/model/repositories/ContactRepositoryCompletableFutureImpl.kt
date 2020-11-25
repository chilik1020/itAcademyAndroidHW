package com.chilik1020.hw8.model.repositories

import android.util.Log
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.model.interactors.FetchContactsInteractor
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.interactors.CreateContactInteractor
import com.chilik1020.hw8.util.LOG_TAG_APP
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.function.Supplier

class ContactRepositoryCompletableFutureImpl(private val contactDao: ContactDao) :
    ContactRepository {

    private val executor = Executors.newSingleThreadExecutor()

    override fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener) {
        Log.d(LOG_TAG_APP, "CompletableFuture: getAllContacts")
        val supplyAsyncList =
            CompletableFuture.supplyAsync(Supplier<List<Contact>> { contactDao.getAll() }, executor)

        listener.onSuccess(supplyAsyncList.get())
    }

    override fun getById(id: String): Contact {
        Log.d(LOG_TAG_APP, "CompletableFuture: getById")
        val supplyAsyncById =
            CompletableFuture.supplyAsync(Supplier<Contact> { contactDao.getById(id) }, executor)
        return supplyAsyncById.get()
    }

    override fun addContact(contact: Contact) {
        Log.d(LOG_TAG_APP, "CompletableFuture: addContact")
        CompletableFuture.runAsync(Runnable { contactDao.add(contact) }, executor)
    }

    override fun editContact(contact: Contact) {
        Log.d(LOG_TAG_APP, "CompletableFuture: editContact")
        CompletableFuture.runAsync(Runnable { contactDao.edit(contact) }, executor)
    }

    override fun removeContact(id: String) {
        Log.d(LOG_TAG_APP, "CompletableFuture: removeContact")
        CompletableFuture.runAsync(Runnable { contactDao.delete(id) }, executor)
    }
}