package com.chilik1020.hw8.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room
import com.chilik1020.hw8.model.local.AppDatabase
import com.chilik1020.hw8.model.local.ContactDao
import com.chilik1020.hw8.util.DATABASE_NAME
import com.chilik1020.hw8.util.LOG_TAG_APP

class ContactProvider : ContentProvider() {

    private lateinit var appDatabase: AppDatabase
    private var contactDao: ContactDao? = null

    override fun onCreate(): Boolean {
        appDatabase = Room.databaseBuilder(context!!, AppDatabase::class.java, DATABASE_NAME)
            .build()
        contactDao = appDatabase.contactDao
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            CONTACTS_ACTION -> {
                contactDao?.getCursor()
            }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        Log.d(LOG_TAG_APP, "ContactProvider:getType")
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(LOG_TAG_APP, "ContactProvider:insert")
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(LOG_TAG_APP, "ContactProvider:delete")
        return -1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.d(LOG_TAG_APP, "ContactProvider:update")
        return -1
    }

    companion object {
        private const val CONTACTS_ACTION = 4201
        private const val CONTACTS_ACTION_EXACT = 42022
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI("com.chilik1020.provider.contact", "data/contacts", CONTACTS_ACTION)
            addURI("com.chilik1020.provider.contact", "data/contacts/#", CONTACTS_ACTION_EXACT)
        }
    }
}