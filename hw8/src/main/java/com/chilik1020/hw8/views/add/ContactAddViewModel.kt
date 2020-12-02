package com.chilik1020.hw8.views.add

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import com.chilik1020.hw8.model.entities.Result
import com.chilik1020.hw8.model.interactors.CreateContactInteractor
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.views.BaseViewModel
import com.chilik1020.hw8.views.SingleLiveEvent
import java.util.*

class ContactAddViewModel(
    contactDao: ContactDao,
    pref: SharedPreferences,
    private val interactor: CreateContactInteractor,
    context: Context
) :
    BaseViewModel(contactDao, pref, context) {

    val contact: LiveData<Contact> = MutableLiveData<Contact>(
        Contact(
            UUID.randomUUID().toString(),
            ContactType.PHONENUMBER,
            "",
            ""
        )
    )
    private val contactInfoHintMutable: MutableLiveData<String> =
        MutableLiveData<String>(ContactType.PHONENUMBER.string)
    val contactInfoHint: LiveData<String>
        get() = contactInfoHintMutable

    val eventMessage = SingleLiveEvent<Result<Long>>()
    private val listener =
        CreateContactInteractor.OnCreateContactListener { eventMessage.value = it }

    fun saveContact() {
        contact.value?.let { interactor.createContact(it, repository, listener) }
    }

    fun setContactType(type: ContactType) {
        contact.value?.type = type
        when (type) {
            ContactType.EMAIL -> contactInfoHintMutable.value = ContactType.EMAIL.string
            else -> contactInfoHintMutable.value = ContactType.PHONENUMBER.string
        }
    }
}