package com.chilik1020.hw42.utils

import android.graphics.Color
import java.util.*
import kotlin.math.sqrt

fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

fun checkIfPointInCircle(
    xP: Float,
    yP: Float,
    xC: Float,
    yC: Float,
    radius: Float
): Boolean {
    return radius > sqrt((xP - xC) * (xP - xC) + (yP - yC) * (yP - yC))
}