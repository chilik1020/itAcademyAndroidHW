package com.chilik1020.hw2.model.entity

sealed class Message {
    object Completed : Message()
    class Next(val value : Int) : Message()
}