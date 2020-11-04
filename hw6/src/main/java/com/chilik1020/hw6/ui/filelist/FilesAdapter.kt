package com.chilik1020.hw6.ui.filelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw6.R
import kotlinx.android.synthetic.main.item_file.view.*

class FilesAdapter : RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    private var listFiles = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun getItemCount() = listFiles.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(listFiles[position])
    }

    fun setData(data: List<String>){
        listFiles = data
        notifyDataSetChanged()
    }

    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(name: String) {
            itemView.tvfileName.text = name
        }
    }
}