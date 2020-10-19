package com.chilik1020.hw41.model.entities

data class Contact(val id: Int, val type: ContactType, var fullname: String, var number: String = "not defined", var email: String = "not defined")

sealed class ContactType{
    object PhoneNumber : ContactType()
    object Email : ContactType()
}