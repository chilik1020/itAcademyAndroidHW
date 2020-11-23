package com.chilik1020.hw6.model

data class FileModel(
    val name: String,
    val path: String,
    val type: FileType
)

enum class FileType {
    FOLDER,
    FILE
}

enum class StorageType(val type: String) {
    INTERNAL("internal"),
    EXTERNAL("external")
}