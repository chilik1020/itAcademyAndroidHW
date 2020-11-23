package com.chilik1020.hw7.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chilik1020.hw7.model.entities.Contact
import com.chilik1020.hw7.model.entities.ContactTypeConverter

@Database(entities = [Contact::class], version = 1, exportSchema = false)
@TypeConverters(ContactTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val contactDao: ContactDao
}