package com.chilik1020.hw2.model.base

interface Observer<T> {
    fun update(msg: T)
}