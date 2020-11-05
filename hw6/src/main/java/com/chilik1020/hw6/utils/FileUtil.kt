package com.chilik1020.hw6.utils

import java.io.File

fun createNewFile(path: String, name: String) {
    File(path, "$name.txt").printWriter().use { out ->
        out.println("some text")
    }
}

fun createNewFolder(path: String, name: String) {
    val folder = File(path, name).mkdir()
}