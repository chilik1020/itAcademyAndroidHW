package com.chilik1020.hw10.presentation

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilik1020.hw10.data.Song
import com.chilik1020.hw10.databinding.ActivityMainBinding
import com.chilik1020.hw10.service.MusicPlayer
import com.chilik1020.hw10.service.MusicService
import com.chilik1020.hw10.service.NotificationUtil
import com.chilik1020.hw10.utils.BASE_LOG
import com.chilik1020.hw10.utils.SONG_NAME_KEY


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var path: String
    private val onSongClickListener: (Song) -> Unit = {
        Log.d(BASE_LOG, "CLICKED $it")
    }
    private val songAdapter = SongAdapter(onSongClickListener)

    private lateinit var musicService: MusicService
    private var isBound: Boolean = false
    private lateinit var notificationUtil: NotificationUtil
    private lateinit var musicPlayer: MusicPlayer

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            musicService = (service as MusicService.MusicBinder).getMusicService()
            isBound = true
            notificationUtil = musicService.notificationUtil
            musicPlayer = musicService.musicPlayer
            checkReadStoragePermissions()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        getSongListFromStorage()
    }

    override fun onStart() {
        super.onStart()
        bindMusicService()
    }

    override fun onStop() {
        super.onStop()
        unBindMusicService()
    }

    private fun initViews() {
        with(binding) {
            setSupportActionBar(toolbarMain)
            recyclerViewFiles.apply {
                adapter = songAdapter
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            }

            btnPlay.setOnClickListener { if (musicPlayer.isPlaying()) musicPlayer.pause() else musicPlayer.play() }
            btnPrevious.setOnClickListener { musicPlayer.previous() }
            btnNext.setOnClickListener { musicPlayer.next() }
        }
    }

    private fun bindMusicService() {
        bindService(
            Intent(this, MusicService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
        val startNotStickyIntent = Intent(this, MusicService::class.java).apply {
            putExtra(SONG_NAME_KEY, "rammstein")
        }
        startService(startNotStickyIntent)
//        musicService.startForeground(10, notificationUtil.createNotification(""))
    }

    private fun unBindMusicService() {
        unbindService(serviceConnection)
        musicService.stopForeground(false)
        isBound = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun checkReadStoragePermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        }
    }

    fun getSongListFromStorage() {
        val songList = mutableListOf<Song>()
        //retrieve song info
        val musicResolver = contentResolver
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor: Cursor? = musicResolver.query(musicUri, null, null, null, null)
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            val titleColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val idColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val artistColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)

            //add songs to list
            do {
                val thisId: Long = musicCursor.getLong(idColumn)
                val thisTitle: String = musicCursor.getString(titleColumn)
                val thisArtist: String = musicCursor.getString(artistColumn)
                songList.add(Song(thisId, thisTitle, thisArtist))
            } while (musicCursor.moveToNext())
        }
        songList.forEach {
            Log.d(BASE_LOG, it.toString())
        }
        songAdapter.setData(songList)
    }
}