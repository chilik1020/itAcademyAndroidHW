package com.chilik1020.hw8.model

import com.chilik1020.hw8.model.entities.Contact
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.function.Supplier

class ContactRepositoryCompletableFutureImpl(private val contactDao: ContactDao) :
    ContactRepository {

    private val executor = Executors.newSingleThreadExecutor()

    override fun getAllContacts(): List<Contact> {
        val supplyAsyncList =
            CompletableFuture.supplyAsync(Supplier<List<Contact>> { contactDao.getAll() }, executor)
        return supplyAsyncList.get()
    }

    override fun getById(id: String): Contact {
        val supplyAsyncById =
            CompletableFuture.supplyAsync(Supplier<Contact> { contactDao.getById(id) }, executor)
        return supplyAsyncById.get()
    }

    override fun addContact(contact: Contact) {
        CompletableFuture.runAsync (Runnable { contactDao.add(contact) }, executor)
    }

    override fun editContact(contact: Contact) {
        CompletableFuture.runAsync (Runnable { contactDao.edit(contact) }, executor)
    }

    override fun removeContact(id: String) {
        CompletableFuture.runAsync (Runnable { contactDao.delete(id) }, executor)
    }
}