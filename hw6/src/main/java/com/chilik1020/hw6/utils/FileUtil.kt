package com.chilik1020.hw6.utils

import java.io.File

fun createNewFile(path: String, name: String) {
    File(path, "$name.txt").createNewFile()
}

fun createNewFolder(path: String, name: String) {
    File(path, name).mkdirs()
}

fun deleteFile(path: String) {
    val file = File(path)
    if (file.isDirectory) {
        file.deleteRecursively()
    } else {
        file.delete()
    }
}