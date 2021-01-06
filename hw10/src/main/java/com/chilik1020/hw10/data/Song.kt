package com.chilik1020.hw10.data

data class Song(
    val id: Long,
    val title: String,
    val trackNumber: Int,
    val year: Int,
    val duration: Int,
    val path: String,
    val albumName: String,
    val artistId: Int,
    val artistName: String,
    var isPlayingNow: Boolean
)