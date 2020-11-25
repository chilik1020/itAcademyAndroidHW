package com.chilik1020.hw8.views.add

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import com.chilik1020.hw8.model.interactors.CreateContactInteractor
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.views.BaseViewModel
import java.util.*

class ContactAddViewModel(
    contactDao: ContactDao,
    pref: SharedPreferences,
    private val interactor: CreateContactInteractor
) :
    BaseViewModel(contactDao, pref) {

    val contact: LiveData<Contact> = MutableLiveData<Contact>(
        Contact(
            UUID.randomUUID().toString(),
            ContactType.PHONENUMBER,
            "",
            ""
        )
    )

    fun saveContact() {
        contact.value?.let { interactor.createContact(it, repository) }
    }
}