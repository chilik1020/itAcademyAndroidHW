package com.chilik1020.hw8.views.list

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.model.interactors.FetchContactsInteractor
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.util.LOG_TAG_APP
import com.chilik1020.hw8.views.BaseViewModel
import java.util.*

class ContactsListViewModel(
    contactDao: ContactDao,
    pref: SharedPreferences,
    private val interactor: FetchContactsInteractor
) :
    BaseViewModel(contactDao, pref) {

    private val contacts = MutableLiveData<List<Contact>>(emptyList())
    private val contactsFilteredMutable = MutableLiveData<List<Contact>>()
    val contactsFiltered: LiveData<List<Contact>>
        get() = contactsFilteredMutable

    val tvNoContactsVisibility =
        Transformations.map(contactsFiltered) { if (it.isEmpty()) View.VISIBLE else View.GONE }

    private var filterHint: String = ""

    private val listener = object: FetchContactsInteractor.OnFetchContactsListener {
        override fun onSuccess(data: List<Contact>) {
            contacts.value = data
            updateFilteredData()
        }

        override fun onError() {
            Log.d(LOG_TAG_APP, "FetchDataError")
        }
    }

    init {
        fetchContacts()
    }

    fun fetchContacts() {
        interactor.fetchData(repository, listener)
    }

    fun updateFilterHint(hint: String) {
        filterHint = hint
        updateFilteredData()
    }

    private fun updateFilteredData() {
        contactsFilteredMutable.value = if (filterHint.isNotEmpty()) {
            contacts.value?.filter {
                it.fullname
                    .toLowerCase(Locale.getDefault())
                    .contains(filterHint.toLowerCase(Locale.getDefault()))
            }
        } else {
            contacts.value
        }
    }
}