package com.chilik1020.hw6.ui.filelist

import com.chilik1020.hw6.model.FileModel

fun interface OnFileItemClickListener {
    fun onClick(file: FileModel)
}