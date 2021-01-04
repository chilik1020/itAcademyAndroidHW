package com.chilik1020.hw11.data

import java.util.*

const val TYPE_EMAIL = "Email"
const val TYPE_PHONENUMBER = "Phonenumber"

data class Contact(
    val id: String = UUID.randomUUID().toString(),
    var type: ContactType,
    var fullname: String,
    var contactInfo: String = "not defined"
)

enum class ContactType(val string: String) {
    EMAIL(TYPE_EMAIL),
    PHONENUMBER(TYPE_PHONENUMBER)
}