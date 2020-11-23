package com.chilik1020.hw8.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.chilik1020.hw8.model.entities.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getById(id : String): Contact

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(contact: Contact)

    @Update
    fun edit(contact: Contact)

    @Query("DELETE FROM contacts WHERE id = :id")
    fun delete(id: String)
}