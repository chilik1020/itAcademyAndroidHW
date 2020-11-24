package com.chilik1020.hw8.views.list

import android.content.SharedPreferences
import com.chilik1020.hw8.model.ContactDao
import com.chilik1020.hw8.views.BaseViewModel

class ContactsListViewModel(contactDao: ContactDao, pref: SharedPreferences) : BaseViewModel(contactDao, pref) {

    fun fetchContacts() = repository.getAllContacts()
}