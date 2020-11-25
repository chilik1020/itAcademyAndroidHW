package com.chilik1020.hw8.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.chilik1020.hw8.model.entities.Contact
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getById(id: String): Contact

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(contact: Contact)

    @Update
    fun edit(contact: Contact)

    @Query("DELETE FROM contacts WHERE id = :id")
    fun delete(id: String)


    @Query("SELECT * FROM contacts")
    fun getAllRx(): Flowable<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getByIdRx(id: String): Flowable<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRx(contact: Contact): Completable

    @Update
    fun editRx(contact: Contact): Completable

    @Query("DELETE FROM contacts WHERE id = :id")
    fun deleteRx(id: String): Completable
}