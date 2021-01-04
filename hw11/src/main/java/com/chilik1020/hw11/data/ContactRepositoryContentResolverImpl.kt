package com.chilik1020.hw11.data

import android.content.ContentResolver
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.chilik1020.hw11.domain.FetchContactsInteractor
import com.chilik1020.hw11.utils.LOG_TAG_APP


class ContactRepositoryContentResolverImpl(private val contentResolver: ContentResolver) :
    ContactRepository {

    private val uiHandler = Handler(Looper.getMainLooper())
    private val workerThread = WorkerThread("worker").apply {
        start()
        prepareHandler()
    }

    override fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener) {
        Log.d(LOG_TAG_APP, "Handler: getAllContacts")
        workerThread.postTask(Runnable {
            val taskToMainThread = try {

                Runnable { listener.onFinish(Result.Success(null)) }
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