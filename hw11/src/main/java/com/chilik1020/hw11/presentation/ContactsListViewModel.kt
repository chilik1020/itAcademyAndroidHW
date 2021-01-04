package com.chilik1020.hw11.presentation

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chilik1020.hw11.data.Contact
import com.chilik1020.hw11.data.ContactRepository
import com.chilik1020.hw11.data.Result
import com.chilik1020.hw11.domain.FetchContactsInteractor
import com.chilik1020.hw11.utils.LOG_TAG_APP
import java.util.*


class ContactsListViewModel(
    private val repository: ContactRepository,
    private val interactor: FetchContactsInteractor
) : ViewModel() {

    private val contacts = MutableLiveData<List<Contact>>(emptyList<Contact>())
    private val contactsFilteredMutable = MutableLiveData<List<Contact>>()
    val contactsFiltered: LiveData<List<Contact>>
        get() = contactsFilteredMutable

    private var filterHint: String = ""

    private val listener =
        FetchContactsInteractor.OnFetchContactsListener { result ->
            when (result) {
                is Result.Success -> {
                    contacts.value = result.data
                    updateFilteredData()
                }
                is Result.Failure -> {
                    Log.d(LOG_TAG_APP, "FetchContactListError")
                }
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