package com.chilik1020.hw10.presentation.filelist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw10.R
import com.chilik1020.hw10.data.FileModel
import com.chilik1020.hw10.data.FileType
import com.chilik1020.hw10.databinding.ItemFileBinding
import com.chilik1020.hw10.utils.BASE_LOG

class FilesAdapter(
    private val listener: (FileModel) -> Unit
) : RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    private var listFiles = listOf<FileModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFileBinding.inflate(inflater, parent, false)
        return FileViewHolder(binding, listener)
    }

    override fun getItemCount() = listFiles.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(listFiles[position])
    }

    fun setData(data: List<FileModel>) {
        listFiles = data
        notifyDataSetChanged()
    }

    class FileViewHolder(
        private val binding: ItemFileBinding,
        private val listener: (FileModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(file: FileModel) {
            binding.tvFileName.text = file.name
            when (file.type) {
                FileType.FOLDER -> binding.ivFileType.setImageResource(R.drawable.ic_folder)
                FileType.MUSIC_FILE -> binding.ivFileType.setImageResource(R.drawable.ic_music_note)
            }
            binding.root.setOnClickListener {
                Log.d(BASE_LOG, "CLICK ADAPTRER")
                listener(file)
            }
        }
    }
}