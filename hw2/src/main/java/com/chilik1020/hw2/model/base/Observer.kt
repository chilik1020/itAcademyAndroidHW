package com.chilik1020.hw2.model.base

fun interface Observer<T> {
    fun update(msg: T)
}