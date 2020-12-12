package com.chilik1020.weatherappmvp.utils

import java.time.Instant
import java.time.format.DateTimeFormatter

fun timeFromStamp(stamp: Long): String {
    return DateTimeFormatter.ISO_INSTANT
        .format(Instant.ofEpochSecond(stamp)).substring(11, 16)
}