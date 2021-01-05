package com.chilik1020.hw10.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw10.data.Song
import com.chilik1020.hw10.databinding.ItemSongBinding

class SongAdapter(
    private val listener: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private val songList = mutableListOf<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(inflater, parent, false)
        return SongViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount() = songList.size

    fun setData(list: List<Song>) {
        songList.clear()
        songList.addAll(list)
        notifyDataSetChanged()
    }

    class SongViewHolder(
        private val binding: ItemSongBinding,
        private val listener: (Song) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.tvFileName.text = song.title
            binding.root.setOnClickListener { listener(song) }
        }
    }
}