package com.chilik1020.hw8.views

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.model.repositories.ContactRepository
import com.chilik1020.hw8.model.repositories.ContactRepositoryCompletableFutureImpl
import com.chilik1020.hw8.model.repositories.ContactRepositoryHandlerImpl
import com.chilik1020.hw8.model.repositories.ContactRepositoryRxJavaImpl
import com.chilik1020.hw8.util.REPOSITORY_TYPE_KEY
import com.chilik1020.hw8.util.TYPE_COMPLETABLE_FUTURE
import com.chilik1020.hw8.util.TYPE_HANDLER
import com.chilik1020.hw8.util.TYPE_RX_JAVA

abstract class BaseViewModel(
    private val contactDao: ContactDao,
    private val pref: SharedPreferences,
    private val context: Context
) : ViewModel() {
    var repository: ContactRepository

    init {
        val type = pref.getString(REPOSITORY_TYPE_KEY, TYPE_COMPLETABLE_FUTURE)
        repository = when (type) {
            TYPE_RX_JAVA -> ContactRepositoryRxJavaImpl(contactDao)
            TYPE_HANDLER -> ContactRepositoryHandlerImpl(contactDao)
            else -> ContactRepositoryCompletableFutureImpl(contactDao, ContextCompat.getMainExecutor(context))
        }
    }
}