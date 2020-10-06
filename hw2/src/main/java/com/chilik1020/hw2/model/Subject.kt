package com.chilik1020.hw2.model

import java.io.Serializable

interface Subject : Serializable {
    fun subscribe(observer: Observer)
    fun unsubscribe(observer: Observer)
    suspend fun startDataTrans()
}