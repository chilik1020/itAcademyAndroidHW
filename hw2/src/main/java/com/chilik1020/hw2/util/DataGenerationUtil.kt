package com.chilik1020.hw2.util

import kotlin.random.Random

fun generateRandomSet(
    minSize: Int = 10,
    maxSize: Int = 50,
    minValue: Int = -100,
    maxValue: Int = 100
): HashSet<Int> {
    val randomSize = randomValue(minSize, maxSize)
    val randomValues = List(randomSize) { randomValue(minValue, maxValue) }
    return randomValues.toHashSet()
}

fun randomValue(min: Int, max: Int) = Random.nextInt(min, max)