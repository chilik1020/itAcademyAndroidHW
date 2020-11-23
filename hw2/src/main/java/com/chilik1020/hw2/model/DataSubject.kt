package com.chilik1020.hw2.model

import com.chilik1020.hw2.model.base.Observer
import com.chilik1020.hw2.model.base.Subject
import com.chilik1020.hw2.model.entity.Message

object DataSubject : Subject<Message> {
    override val observers: MutableList<Observer<Message>> = mutableListOf()
}