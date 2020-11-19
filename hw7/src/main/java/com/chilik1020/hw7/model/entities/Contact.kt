package com.chilik1020.hw7.model.entities

import java.util.*

data class Contact(
    val id: UUID,
    val type: ContactType,
    var fullname: String,
    var contactInfo: String = "not defined"
)

enum class ContactType {
    EMAIL,
    PHONENUMBER
}