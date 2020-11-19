package com.chilik1020.hw41.model

import com.chilik1020.hw41.model.entities.Contact
import com.chilik1020.hw41.model.entities.ContactType
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class ContactRepositoryAppClassImplTest {
    companion object {
        const val DATA_SIZE = 6
    }

    private val data: MutableList<Contact> = mutableListOf()

    lateinit var repository: ContactRepository

    @Before
    fun before() {
        for (i in 0 until DATA_SIZE) {
            data.add(
                Contact(
                    UUID.randomUUID(),
                    ContactType.PHONENUMBER,
                    "user$i",
                    "contactInfo$i"
                )
            )
        }
        val list = mutableListOf<Contact>()
        list.addAll(data)
        SimpleDataStorage.contacts = list
        repository = ContactRepositoryAppClassImpl(SimpleDataStorage)
    }

    @After
    fun after() {
        data.clear()
    }

    @Test
    fun getAllContacts() {
        Assert.assertEquals(DATA_SIZE, repository.getAllContacts().size)
    }

    @Test
    fun getById() {
        val firstUser = data.first()
        Assert.assertEquals(firstUser, repository.getById(firstUser.id))
    }

    @Test
    fun addContact() {
        val newUser =
            Contact(UUID.randomUUID(), ContactType.PHONENUMBER, "user6", "contactInfo6")
        repository.addContact(newUser)
        Assert.assertEquals(DATA_SIZE + 1, repository.getAllContacts().size)
    }

    @Test
    fun editContact() {
        val firstUser = data.first()
        firstUser.fullname = "newFullname"
        firstUser.contactInfo = "newPhoneNumber"
        repository.editContact(firstUser)
        Assert.assertEquals("newFullname", repository.getById(firstUser.id).fullname)
        Assert.assertEquals("newPhoneNumber", repository.getById(firstUser.id).contactInfo)
    }

    @Test
    fun removeContact() {
        val lastUser = data.last()
        repository.removeContact(lastUser.id)
        Assert.assertEquals(DATA_SIZE - 1, repository.getAllContacts().size)
    }
}