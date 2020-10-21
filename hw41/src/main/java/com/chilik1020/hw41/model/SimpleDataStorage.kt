package com.chilik1020.hw41.model

import com.chilik1020.hw41.model.entities.Contact
import com.chilik1020.hw41.model.entities.ContactType
import java.util.*

object SimpleDataStorage {
    val contacts = mutableListOf<Contact>()

    init {
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