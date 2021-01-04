package com.chilik1020.hw10.data

data class FileModel(
    val name: String,
    val path: String,
    val type: FileType
)

enum class FileType {
    FOLDER,
    MUSIC_FILE
}