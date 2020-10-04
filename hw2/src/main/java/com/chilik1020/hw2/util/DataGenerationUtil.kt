package com.chilik1020.hw2.util

import kotlin.random.Random

fun generateRandomSet(
    minSize: Int = 10,
    maxSize: Int = 50,
    minValue: Int = -100,
    maxValue: Int = 100
): HashSet<Int> {
    val randomSize = Random.nextInt(minSize, maxSize)
    val randomValues = List(randomSize) { Random.nextInt(minValue, maxValue) }
    return randomValues.toHashSet()
}