package com.chilik1020.hw8.model.repositories

import android.util.Log
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.Result
import com.chilik1020.hw8.model.interactors.CreateContactInteractor
import com.chilik1020.hw8.model.interactors.EditContactInteractor
import com.chilik1020.hw8.model.interactors.FetchContactsInteractor
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.util.LOG_TAG_APP
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactRepositoryRxJavaImpl(private val contactDao: ContactDao) : ContactRepository {

    override fun getAllContacts(listener: FetchContactsInteractor.OnFetchContactsListener) {
        Log.d(LOG_TAG_APP, "RxJava: getAllContacts")
        val subscribe = contactDao.getAllRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listener.onFinish(Result.Success(it)) },
                { listener.onFinish(Result.Failure(it)) }
            )
    }

    override fun getById(
        id: String,
        listener: EditContactInteractor.OnFetchContactByIdListener
    ) {
        Log.d(LOG_TAG_APP, "RxJava: getById")
        val subscribe = contactDao.getByIdRx(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listener.onFinish(Result.Success(it)) },
                { listener.onFinish(Result.Failure(it)) }
            )
    }

    override fun addContact(
        contact: Contact,
        listener: CreateContactInteractor.OnCreateContactListener
    ) {
        Log.d(LOG_TAG_APP, "RxJava: addContact")
        val subscribe = contactDao.addRx(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listener.onFinish(Result.Success(it)) },
                { listener.onFinish(Result.Failure(it)) }
            )
    }

    override fun editContact(
        contact: Contact,
        listener: EditContactInteractor.OnEditContactListener
    ) {
        Log.d(LOG_TAG_APP, "RxJava: editContact")
        val subscribe = contactDao.editRx(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listener.onFinish(Result.Success(it)) },
                { listener.onFinish(Result.Failure(it)) }
            )
    }

    override fun removeContact(
        id: String,
        listener: EditContactInteractor.OnDeleteContactListener
    ) {
        Log.d(LOG_TAG_APP, "RxJava: removeContact")
        val subscribe = contactDao.deleteRx(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listener.onFinish(Result.Success(it)) },
                { listener.onFinish(Result.Failure(it)) }
            )
    }
}