package com.chilik1020.hw8.views.add

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.ContactDao
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import com.chilik1020.hw8.util.LOG_TAG_APP
import com.chilik1020.hw8.views.BaseViewModel
import java.util.*

class ContactAddViewModel(contactDao: ContactDao, pref: SharedPreferences) :
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
        Log.d(LOG_TAG_APP, "Contact to save  ${this.contact.value}")
        contact.value?.let { repository.addContact(it) }
    }
}