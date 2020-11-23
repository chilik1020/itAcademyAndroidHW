package com.chilik1020.hw42.utils

import android.graphics.Color
import java.util.*
import kotlin.math.sqrt

fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

fun checkIfPointInCircle(
    xPoint: Float,
    yPoint: Float,
    xCenter: Float,
    yCenter: Float,
    radius: Float
) = radius > sqrt((xPoint - xCenter) * (xPoint - xCenter) + (yPoint - yCenter) * (yPoint - yCenter))
