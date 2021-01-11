package com.chilik1020.hw11.data.repositories

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.chilik1020.hw11.data.entities.Result
import com.chilik1020.hw11.data.sources.ContactsSource
import com.chilik1020.hw11.domain.FetchContactsInteractor


class ContactRepositoryImpl(private val source: ContactsSource) :
    ContactRepository {

    private val uiHandler = Handler(Looper.getMainLooper())
    private val workerThread = WorkerThread("worker").apply {
        start()
        prepareHandler()
    }

    override fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener) {
        workerThread.postTask(Runnable {
            val taskToMainThread = try {
                val data = source.getContacts()
                Runnable { listener.onFinish(Result.Success(data)) }
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