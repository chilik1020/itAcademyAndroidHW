package com.chilik1020.hw11.data.sources

import com.chilik1020.hw11.data.entities.Contact

interface ContactsSource {
    fun getContacts(): List<Contact>
}