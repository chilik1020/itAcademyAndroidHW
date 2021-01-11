package com.chilik1020.hw10.presentation

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilik1020.hw10.R
import com.chilik1020.hw10.data.Song
import com.chilik1020.hw10.data.SongProvider
import com.chilik1020.hw10.databinding.ActivityMainBinding
import com.chilik1020.hw10.service.MusicPlayer
import com.chilik1020.hw10.service.MusicService
import com.chilik1020.hw10.utils.BASE_LOG
import com.chilik1020.hw10.utils.CLOSE_ACT_REQUEST
import com.chilik1020.hw10.utils.CLOSE_ACT_RESULT
import com.chilik1020.hw10.utils.PENDING_INTENT


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val onSongClickListener: (Song) -> Unit = {
        musicPlayer?.setSong(it)
        updateCurrentSong(it)
    }

    private val songAdapter = SongAdapter(onSongClickListener)

    private var songList: List<Song> = listOf()
    private var currentSong: Song? = null

    private lateinit var musicServiceIntent: Intent
    private var musicService: MusicService? = null
    private var isBound: Boolean = false
    private var musicPlayer: MusicPlayer? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            musicService = (service as MusicService.MusicBinder).getMusicService()
            isBound = true
            musicService?.let {
                musicPlayer = it.musicPlayer.apply {
                    setPlayList(songList)
                }
                musicPlayer?.let { player ->
                    player.currentSongLiveData.observe(this@MainActivity) { updateCurrentSong(it) }
                    player.playerStatus.observe(this@MainActivity) { changeBtnIconDrawable() }
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        checkReadStoragePermissions()
        songList = SongProvider.getAllDeviceSongs(applicationContext)
        Log.d(BASE_LOG, songList.toString())
        songAdapter.setData(songList)
        bindMusicService()
    }

    override fun onDestroy() {
        unBindMusicService()
        super.onDestroy()
    }

    private fun initViews() {
        with(binding) {
            setSupportActionBar(toolbarMain)
            recyclerViewFiles.apply {
                adapter = songAdapter
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            }

            btnPlay.setOnClickListener {
                musicPlayer?.let {
                    if (it.isPlaying()) {
                        it.pause()
                    } else {
                        it.play()
                    }
                }
            }
            btnPrevious.setOnClickListener { musicPlayer?.previous() }
            btnNext.setOnClickListener { musicPlayer?.next() }
        }
    }

    private fun updateCurrentSong(newSong: Song) {
        currentSong?.isPlayingNow = false
        newSong.isPlayingNow = true
        songAdapter.apply {
            notifyItemChanged(songList.indexOf(currentSong))
            notifyItemChanged(songList.indexOf(newSong))
        }
        currentSong = newSong
    }

    private fun changeBtnIconDrawable() {
        musicPlayer?.let {
            val playBtnIconId = when (it.isPlaying()) {
                false -> R.drawable.ic_play
                true -> R.drawable.ic_pause
            }
            binding.btnPlay.background =
                ContextCompat.getDrawable(applicationContext, playBtnIconId)
        }
    }

    private fun bindMusicService() {

        val pendingIntent = createPendingResult(CLOSE_ACT_REQUEST, Intent(), 0)
        musicServiceIntent =
            Intent(this, MusicService::class.java).putExtra(PENDING_INTENT, pendingIntent)
        bindService(
            musicServiceIntent,
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    private fun unBindMusicService() {
        unbindService(serviceConnection)
        isBound = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == CLOSE_ACT_RESULT) {
            finish()
        }
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
}