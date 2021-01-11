package com.chilik1020.weatherappmvp.utils

import java.time.Instant
import java.time.format.DateTimeFormatter

fun Long.timeFromStamp(): String {
    return DateTimeFormatter.ISO_INSTANT
        .format(Instant.ofEpochSecond(this)).substring(11, 16)
}