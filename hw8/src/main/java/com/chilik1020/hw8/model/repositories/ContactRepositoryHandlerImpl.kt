package com.chilik1020.hw8.model.repositories

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.Result
import com.chilik1020.hw8.model.interactors.CreateContactInteractor
import com.chilik1020.hw8.model.interactors.EditContactInteractor
import com.chilik1020.hw8.model.interactors.FetchContactsInteractor
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.util.LOG_TAG_APP

class ContactRepositoryHandlerImpl(private val contactDao: ContactDao) : ContactRepository {

    private val uiHandler = Handler(Looper.getMainLooper())
    private val workerThread = WorkerThread("worker").apply {
        start()
        prepareHandler()
    }

    override fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener) {
        Log.d(LOG_TAG_APP, "Handler: getAllContacts")
        workerThread.postTask(Runnable {
            val data = contactDao.getAll()
            uiHandler.post(Runnable { listener.onFinish(Result.Success(data)) })
        })
    }

    override fun getById(
        id: String,
        listener: EditContactInteractor.OnFetchContactByIdListener
    ) {
        Log.d(LOG_TAG_APP, "Handler: getById")
        workerThread.postTask(Runnable {
            val contact = contactDao.getById(id)
            uiHandler.post(Runnable { listener.onFinish(Result.Success(contact)) })
        })
    }

    override fun addContact(
        contact: Contact,
        listener: CreateContactInteractor.OnCreateContactListener
    ) {
        Log.d(LOG_TAG_APP, "Handler: addContact")
        workerThread.postTask(Runnable { contactDao.add(contact) })
    }

    override fun editContact(contact: Contact) {
        Log.d(LOG_TAG_APP, "Handler: editContact")
        workerThread.postTask(Runnable { contactDao.edit(contact) })
    }

    override fun removeContact(id: String) {
        Log.d(LOG_TAG_APP, "Handler: removeContact")
        workerThread.postTask(Runnable { contactDao.delete(id) })
    }

    class WorkerThread(name: String) : HandlerThread(name) {
        private lateinit var workerHandler: Handler

        fun prepareHandler() {
            workerHandler = Handler(looper)
        }

        fun postTask(task: Runnable) {
            workerHandler.post(task)
        }
    }
}