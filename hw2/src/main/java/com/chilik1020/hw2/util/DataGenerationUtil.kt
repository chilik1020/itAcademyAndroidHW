package com.chilik1020.hw2.util

import kotlin.random.Random

fun generateRandomData(
    minSize: Int = 0,
    maxSize: Int = 50,
    minValue: Int = -100,
    maxValue: Int = 100
): ArrayList<Int> {
    val randomSize = randomValue(minSize, maxSize)
    val values = List(randomSize) { randomValue(minValue, maxValue) }
    val hashSet = values.toSet()
    val arrayList = arrayListOf<Int>()
    arrayList.addAll(hashSet)
    return arrayList
}

fun randomValue(min: Int, max: Int) = Random.nextInt(min, max)