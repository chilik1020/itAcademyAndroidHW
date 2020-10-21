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
                    ContactType.PhoneNumber,
                    "user$i",
                    "phone$i",
                    "email$i"
                )
            )
        }
        val list = mutableListOf<Contact>()
        list.addAll(data)
        repository = ContactRepositoryAppClassImpl(list)
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
            Contact(UUID.randomUUID(), ContactType.PhoneNumber, "user6", "phone6", "email6")
        repository.addContact(newUser)
        Assert.assertEquals(DATA_SIZE + 1, repository.getAllContacts().size)
    }

    @Test
    fun editContact() {
        val firstUser = data.first()
        firstUser.fullname = "newFullname"
        firstUser.number = "newPhoneNumber"
        firstUser.email = "newEmail"
        repository.editContact(firstUser)
        Assert.assertEquals("newFullname", repository.getById(firstUser.id).fullname)
        Assert.assertEquals("newPhoneNumber", repository.getById(firstUser.id).number)
        Assert.assertEquals("newEmail", repository.getById(firstUser.id).email)
    }

    @Test
    fun removeContact() {
        val lastUser = data.last()
        repository.removeContact(lastUser.id)
        Assert.assertEquals(DATA_SIZE - 1, repository.getAllContacts().size)
    }
}