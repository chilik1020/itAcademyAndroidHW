package com.chilik1020.hw8.views.edit

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import com.chilik1020.hw8.model.entities.Result
import com.chilik1020.hw8.model.interactors.EditContactInteractor
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.util.LOG_TAG_APP
import com.chilik1020.hw8.views.BaseViewModel

class ContactEditViewModel(
    contactDao: ContactDao,
    pref: SharedPreferences,
    private val interactor: EditContactInteractor,
    context: Context
) :
    BaseViewModel(contactDao, pref, context) {

    private val contactLiveDataMutable = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = contactLiveDataMutable

    private val hintTextContactInfoMutable = MutableLiveData<String>()
    val hintTextContactInfo: LiveData<String>
        get() = hintTextContactInfoMutable

    private val listenerFetchById = EditContactInteractor.OnFetchContactByIdListener {
        when (it) {
            is Result.Success -> {
                contactLiveDataMutable.value = it.data
                updateHintText()
            }
            is Result.Failure -> {
                Log.d(LOG_TAG_APP, "FetchContactByIdError")
            }
        }
    }

    private val listenerEditContact = EditContactInteractor.OnEditContactListener {
        when (it) {
            is Result.Success -> {
                Log.d(LOG_TAG_APP, "Contact updated")
            }
            is Result.Failure -> {
                Log.d(LOG_TAG_APP, "EditContactError")
            }
        }
    }

    private val listenerDeleteContact = EditContactInteractor.OnDeleteContactListener {
        when (it) {
            is Result.Success -> {
                Log.d(LOG_TAG_APP, "Contact deleted")
            }
            is Result.Failure -> {
                Log.d(LOG_TAG_APP, "DeleteContactError")
            }
        }
    }

    fun getContactById(id: String) {
        interactor.fetchContactById(id, repository, listenerFetchById)
    }

    fun deleteContact() {
        contact.value?.let {
            interactor.deleteContact(it.id, repository, listenerDeleteContact)
        }
    }

    fun editContact() {
        contact.value?.let {
            interactor.editContact(it, repository, listenerEditContact)
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