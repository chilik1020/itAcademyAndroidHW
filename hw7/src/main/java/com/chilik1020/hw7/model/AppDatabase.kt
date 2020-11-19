package com.chilik1020.hw7.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chilik1020.hw7.model.entities.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao()
}