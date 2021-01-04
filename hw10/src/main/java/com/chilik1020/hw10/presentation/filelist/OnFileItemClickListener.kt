package com.chilik1020.hw10.presentation.filelist

import com.chilik1020.hw10.data.FileModel

fun interface OnFileItemClickListener {
    fun onClick(file: FileModel)
}