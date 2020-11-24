package com.chilik1020.hw8.views.list

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.ContactDao
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.views.BaseViewModel

class ContactsListViewModel(contactDao: ContactDao, pref: SharedPreferences) :
    BaseViewModel(contactDao, pref) {

    private val contactsMutable = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = contactsMutable

    private val contactsFilteredMutable = MutableLiveData<List<Contact>>()
    val contactsFiltered: LiveData<List<Contact>>
        get() = contactsFilteredMutable

    init {
        fetchContacts()
    }

    fun fetchContacts(): List<Contact> {
        val cs = repository.getAllContacts()
        contactsMutable.value = cs
        return cs
    }

    fun filterDataWithHint(hint: String) {

    }
}