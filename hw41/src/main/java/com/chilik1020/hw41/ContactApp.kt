package com.chilik1020.hw41

import android.app.Application
import com.chilik1020.hw41.di.repositoryModule
import com.chilik1020.hw41.model.entities.Contact
import com.chilik1020.hw41.model.entities.ContactType
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*

class ContactApp : Application() {

    companion object {
        val contacts = mutableListOf<Contact>()
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initContacts()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ContactApp)
            modules(repositoryModule)
        }
    }

    private fun initContacts() {
        contacts.add(
            Contact(
                UUID.randomUUID(),
                ContactType.PhoneNumber,
                "John Connor",
                number = "(781)373-5577"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID(),
                ContactType.PhoneNumber,
                "Sara Connor",
                number = "(781)373-5577"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID(),
                ContactType.Email,
                "Eric Cartman",
                email = "eric@southpark.com"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID(),
                ContactType.PhoneNumber,
                "Kyle Broflovski",
                number = "(720)351-5555"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID(),
                ContactType.Email,
                "Kenny McCormick",
                email = "misterion@southpark.com"
            )
        )
    }
}