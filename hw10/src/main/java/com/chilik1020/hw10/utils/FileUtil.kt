package com.chilik1020.hw10.utils

import com.chilik1020.hw10.data.FileModel
import com.chilik1020.hw10.data.FileType
import java.io.File

fun createFileModelsListFromDirPath(path: String): List<FileModel> {
    return File(path).listFiles()?.map { fileInMap ->
        FileModel(
            fileInMap.name,
            fileInMap.absolutePath,
            type = if (fileInMap.isDirectory) FileType.FOLDER else FileType.MUSIC_FILE
        )
    }?.sortedBy { fileInSort ->
        fileInSort.type
    }
        ?: emptyList()
}