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
            val taskToMainThread = try {
                val data = contactDao.getAll()
                Runnable { listener.onFinish(Result.Success(data)) }
            } catch (ex: Exception) {
                Runnable { listener.onFinish(Result.Failure(ex)) }
            }
            uiHandler.post(taskToMainThread)
        })
    }

    override fun getById(
        id: String,
        listener: EditContactInteractor.OnFetchContactByIdListener
    ) {
        Log.d(LOG_TAG_APP, "Handler: getById")
        workerThread.postTask(Runnable {
            val taskToMainThread = try {
                val data = contactDao.getById(id)
                Runnable { listener.onFinish(Result.Success(data)) }
            } catch (ex: Exception) {
                Runnable { listener.onFinish(Result.Failure(ex)) }
            }
            uiHandler.post(taskToMainThread)
        })
    }

    override fun addContact(
        contact: Contact,
        listener: CreateContactInteractor.OnCreateContactListener
    ) {
        Log.d(LOG_TAG_APP, "Handler: addContact")
        workerThread.postTask(Runnable {
            val taskToMainThread = try {
                val newContactId = contactDao.add(contact)
                Runnable { listener.onFinish(Result.Success(newContactId)) }
            } catch (ex: Exception) {
                Runnable { listener.onFinish(Result.Failure(ex)) }
            }
            uiHandler.post(taskToMainThread)
        })
    }

    override fun editContact(
        contact: Contact,
        listener: EditContactInteractor.OnEditContactListener
    ) {
        Log.d(LOG_TAG_APP, "Handler: editContact")
        workerThread.postTask(Runnable {
            val taskToMainThread = try {
                val numberOfUpdatedRows = contactDao.edit(contact)
                Runnable { listener.onFinish(Result.Success(numberOfUpdatedRows)) }
            } catch (ex: Exception) {
                Runnable { listener.onFinish(Result.Failure(ex)) }
            }
            uiHandler.post(taskToMainThread)
        })
    }

    override fun removeContact(
        id: String,
        listener: EditContactInteractor.OnDeleteContactListener
    ) {
        Log.d(LOG_TAG_APP, "Handler: removeContact")
        workerThread.postTask(Runnable {
            val taskToMainThread = try {
                val numberOfDeletedRows = contactDao.delete(id)
                Runnable { listener.onFinish(Result.Success(numberOfDeletedRows)) }
            } catch (ex: Exception) {
                Runnable { listener.onFinish(Result.Failure(ex)) }
            }
            uiHandler.post(taskToMainThread)
        })
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