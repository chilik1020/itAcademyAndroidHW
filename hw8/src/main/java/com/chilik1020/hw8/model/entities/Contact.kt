package com.chilik1020.hw8.model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

const val TYPE_EMAIL = "email"
const val TYPE_PHONENUMBER = "phonenumber"

@Entity(tableName = "contacts")
data class Contact(
    @NonNull
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val type: ContactType,
    var fullname: String,
    var contactInfo: String = "not defined"
)

enum class ContactType(val string: String) {
    EMAIL(TYPE_EMAIL),
    PHONENUMBER(TYPE_PHONENUMBER)
}

object ContactTypeConverter {
    @TypeConverter
    @JvmStatic
    fun fromContactType(type: ContactType) = type.string

    @TypeConverter
    @JvmStatic
    fun toContactType(value: String) = when (value) {
        TYPE_EMAIL -> ContactType.EMAIL
        else -> ContactType.PHONENUMBER
    }
}