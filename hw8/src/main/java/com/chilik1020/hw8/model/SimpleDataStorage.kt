package com.chilik1020.hw8.model

import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import java.util.*

object SimpleDataStorage {
    var contacts = mutableListOf<Contact>()

    init {
        contacts.add(
            Contact(
                UUID.randomUUID().toString(),
                ContactType.PHONENUMBER,
                "John Connor",
                contactInfo = "(781)373-5577"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID().toString(),
                ContactType.PHONENUMBER,
                "Sara Connor",
                contactInfo = "(781)373-5577"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID().toString(),
                ContactType.EMAIL,
                "Eric Cartman",
                contactInfo = "eric@southpark.com"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID().toString(),
                ContactType.PHONENUMBER,
                "Kyle Broflovski",
                contactInfo = "(720)351-5555"
            )
        )
        contacts.add(
            Contact(
                UUID.randomUUID().toString(),
                ContactType.EMAIL,
                "Kenny McCormick",
                contactInfo = "misterion@southpark.com"
            )
        )
    }
}