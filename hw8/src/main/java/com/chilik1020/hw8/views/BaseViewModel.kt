package com.chilik1020.hw8.views

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.chilik1020.hw8.model.ContactDao
import com.chilik1020.hw8.model.ContactRepository
import com.chilik1020.hw8.model.ContactRepositoryCompletableFutureImpl
import com.chilik1020.hw8.model.ContactRepositoryHandlerImpl
import com.chilik1020.hw8.model.ContactRepositoryRxJavaImpl
import com.chilik1020.hw8.util.LOG_TAG_APP
import com.chilik1020.hw8.util.REPOSITORY_TYPE_KEY
import com.chilik1020.hw8.util.TYPE_COMPLETABLE_FUTURE
import com.chilik1020.hw8.util.TYPE_EXECUTOR_HANDLER
import com.chilik1020.hw8.util.TYPE_RX_JAVA

abstract class BaseViewModel(
    private val contactDao: ContactDao,
    private val pref: SharedPreferences
) : ViewModel() {
    lateinit var repository: ContactRepository

    init {
        val type = pref.getString(REPOSITORY_TYPE_KEY, TYPE_COMPLETABLE_FUTURE)
        repository = when (type) {
            TYPE_RX_JAVA -> ContactRepositoryRxJavaImpl(contactDao)
            TYPE_EXECUTOR_HANDLER -> ContactRepositoryHandlerImpl(contactDao)
            else -> ContactRepositoryCompletableFutureImpl(contactDao)
        }
    }
}