package com.chilik1020.hw8.views.add

import android.content.SharedPreferences
import com.chilik1020.hw8.model.ContactDao
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.views.BaseViewModel

class ContactAddViewModel(contactDao: ContactDao, pref: SharedPreferences) :
    BaseViewModel(contactDao, pref) {

    fun addContact(contact: Contact) {
        repository.addContact(contact)
    }
}