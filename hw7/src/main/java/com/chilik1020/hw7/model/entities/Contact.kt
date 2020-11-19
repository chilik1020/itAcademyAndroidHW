package com.chilik1020.hw7.model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "contacts")
data class Contact(
    @NonNull
    @PrimaryKey
    val id: UUID,
    val type: ContactType,
    var fullname: String,
    var contactInfo: String = "not defined"
)

enum class ContactType {
    EMAIL,
    PHONENUMBER
}