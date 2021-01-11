package com.chilik1020.hw11.data.sources

import android.content.ContentResolver
import android.net.Uri
import com.chilik1020.hw11.data.entities.Contact
import com.chilik1020.hw11.data.entities.ContactTypeConverter
import com.chilik1020.hw11.utils.URI_CONTACTS

const val COLUMN_ID = "id"
const val COLUMN_TYPE = "type"
const val COLUMN_FULLNAME = "fullname"
const val COLUMN_CONTACT_INFO = "contactInfo"

class ContactSourceContentResolverImpl(private val contentResolver: ContentResolver) :
    ContactsSource {
    override fun getContacts(): List<Contact> {
        val cursor = contentResolver.query(
            Uri.parse(URI_CONTACTS),
            arrayOf(COLUMN_ID, COLUMN_TYPE, COLUMN_FULLNAME, COLUMN_CONTACT_INFO),
            null,
            null,
            null
        )

        cursor?.let { cursor ->
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val typeIndex = cursor.getColumnIndex(COLUMN_TYPE)
            val fullnameIndex = cursor.getColumnIndex(COLUMN_FULLNAME)
            val contactInfoIndex = cursor.getColumnIndex(COLUMN_CONTACT_INFO)
            if (!cursor.moveToFirst()) {
                return emptyList()
            }
            val result = mutableListOf<Contact>()
            do {
                val id = cursor.getString(idIndex)
                val type = ContactTypeConverter.toContactType(cursor.getString(typeIndex))
                val fullname = cursor.getString(fullnameIndex)
                val contactInfo = cursor.getString(contactInfoIndex)
                result.add(
                    Contact(id, type, fullname, contactInfo)
                )
            } while (cursor.moveToNext())
            return result
        }
        return emptyList()
    }
}