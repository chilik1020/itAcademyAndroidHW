package com.chilik1020.hw8.views.edit

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.ContactDao
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import com.chilik1020.hw8.views.BaseViewModel

class ContactEditViewModel(contactDao: ContactDao, pref: SharedPreferences) :
    BaseViewModel(contactDao, pref) {

    private val contactLiveDataMutable = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = contactLiveDataMutable

    private val hintTextContactInfoMutable = MutableLiveData<String>()
    val hintTextContactInfo: LiveData<String>
        get() = hintTextContactInfoMutable

    fun getContactById(id: String) {
        contactLiveDataMutable.value = repository.getById(id)
        updateHintText()
    }

    fun deleteContact() {
        contact.value?.let {
            repository.removeContact(it.id)
        }
    }

    fun editContact() {
        contact.value?.let {
            repository.editContact(it)
        }
    }

    private fun updateHintText() {
        contact.value?.let {
            hintTextContactInfoMutable.value = when (it.type) {
                ContactType.PHONENUMBER -> "Phonenumber"
                ContactType.EMAIL -> "Email"
            }
        }
    }
}