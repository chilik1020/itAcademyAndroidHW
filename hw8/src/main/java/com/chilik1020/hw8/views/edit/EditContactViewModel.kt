package com.chilik1020.hw8.views.edit

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.ContactDao
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.views.BaseViewModel

class EditContactViewModel(contactDao: ContactDao, pref: SharedPreferences) : BaseViewModel(contactDao,pref) {

    private val contactLiveDataMutable: MutableLiveData<Contact> = MutableLiveData()
    val contactLiveData : LiveData<Contact> = contactLiveDataMutable

    fun getContactById(id: String): Contact {
        val contact = repository.getById(id)
        contactLiveDataMutable.value = contact
        return contact
    }
}