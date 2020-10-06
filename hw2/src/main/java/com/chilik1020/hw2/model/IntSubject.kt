package com.chilik1020.hw2.model

import android.util.Log
import kotlinx.coroutines.delay


class IntSubject : Subject {
    private val LOG_TAG = "AppTag:IntSubject"

    private var observers: MutableList<Observer> = mutableListOf()
    private val subscribed: Boolean
        get() = observers.size > 0

    override fun subscribe(observer: Observer) {
        observers.add(observer)
        Log.d(LOG_TAG, "$observer subscribed")
        Log.d(LOG_TAG, "Subscribed : $subscribed")
    //    sendMessage(Message.Next(42))
    }

    override fun unsubscribe(observer: Observer) {
        observers.remove(observer)
        Log.d(LOG_TAG, "$observer unsubscribed")
    }

    override suspend fun startDataTrans() {
        var count = 0
        while (true) {
            if (subscribed) {
                if (count == 5)
                    finish()
                else {
                    Log.d(LOG_TAG, "next value")
                    nextValue(count++)
                }

            }
            Log.d(LOG_TAG, "Subscribed : $subscribed")
            delay(1000)
        }
    }

    private fun nextValue(value: Int) {
        sendMessage(Message.Next(value))
    }

    private fun finish() {
        sendMessage(Message.Completed)
    }

    private fun sendMessage(msg: Message) {
        observers.forEach { it.update(msg) }
    }
}