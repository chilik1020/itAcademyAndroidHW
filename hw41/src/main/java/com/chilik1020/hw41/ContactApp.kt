package com.chilik1020.hw41

import android.app.Application
import com.chilik1020.hw41.model.entities.Contact

class ContactApp : Application() {

    companion object {
        val contacts = mutableListOf<Contact>()
    }

    override fun onCreate() {
        super.onCreate()

        for (i in 0..19) {
            contacts.add(Contact(i.toLong(),i % 2 == 0, "Aaron Bennet", "+3752258734546", "email@gmail.com"))
        }
    }
}