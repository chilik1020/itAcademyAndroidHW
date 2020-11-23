package com.chilik1020.hw2.model.base

import com.chilik1020.hw2.model.DataSubject

interface Subject<T> {
    val observers: MutableList<Observer<T>>
    val subscribed: Boolean
        get() = DataSubject.observers.size > 0

    fun subscribe(observer: Observer<T>) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    fun unsubscribe(observer: Observer<T>) {
        observers.remove(observer)
    }
    fun notify(msg: T) {
        observers.forEach { it.update(msg) }
    }
}