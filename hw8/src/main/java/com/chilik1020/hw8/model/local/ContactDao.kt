package com.chilik1020.hw8.model.local

import android.database.Cursor
import androidx.room.*
import com.chilik1020.hw8.model.entities.Contact
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getCursor(): Cursor

    @Query("SELECT * FROM contacts")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getById(id: String): Contact

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(contact: Contact): Long

    @Update
    fun edit(contact: Contact): Int

    @Query("DELETE FROM contacts WHERE id = :id")
    fun delete(id: String): Int


    @Query("SELECT * FROM contacts")
    fun getAllRx(): Flowable<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getByIdRx(id: String): Flowable<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRx(contact: Contact): Maybe<Long>

    @Update
    fun editRx(contact: Contact): Single<Int>

    @Query("DELETE FROM contacts WHERE id = :id")
    fun deleteRx(id: String): Single<Int>
}