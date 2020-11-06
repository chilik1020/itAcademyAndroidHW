package com.chilik1020.hw6.utils

import com.chilik1020.hw6.model.FileModel
import com.chilik1020.hw6.model.FileType
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

fun createFileModelsListFromDirPath(path: String): List<FileModel> {
    return File(path).listFiles()?.map { fileInMap ->
        FileModel(
            fileInMap.name,
            fileInMap.absolutePath,
            type = if (fileInMap.isDirectory) FileType.FOLDER else FileType.FILE
        )
    }?.sortedBy { fileInSort ->
        fileInSort.type
    }
        ?: emptyList()
}

fun readTextFromFile(path: String) = File(path).readText()