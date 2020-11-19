package com.chilik1020.hw7.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAllContacts()
}