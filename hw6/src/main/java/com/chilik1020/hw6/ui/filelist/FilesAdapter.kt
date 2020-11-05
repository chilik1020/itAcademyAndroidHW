package com.chilik1020.hw6.ui.filelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw6.R
import com.chilik1020.hw6.model.FileModel
import com.chilik1020.hw6.model.FileType
import kotlinx.android.synthetic.main.item_file.view.*

class FilesAdapter(private val listener: OnFileItemClickListener?) :
    RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    private var listFiles = listOf<FileModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view, listener)
    }

    override fun getItemCount() = listFiles.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(listFiles[position])
    }

    fun setData(data: List<FileModel>) {
        listFiles = data
        notifyDataSetChanged()
    }

    class FileViewHolder(itemView: View, private val listener: OnFileItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var fileModel: FileModel

        init {
            itemView.setOnClickListener { listener?.onClick(fileModel) }
            itemView.setOnLongClickListener { listener?.onLongClick(fileModel)?: false }
        }

        fun bind(file: FileModel) {
            fileModel = file
            itemView.tvFileName.text = file.name
            when (file.type) {
                FileType.FOLDER -> itemView.ivFileType.setImageResource(R.drawable.ic_folder)
                FileType.FILE -> itemView.ivFileType.setImageResource(R.drawable.ic_file_text)
            }
        }
    }
}