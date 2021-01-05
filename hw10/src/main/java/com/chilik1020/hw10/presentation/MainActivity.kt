package com.chilik1020.hw10.presentation

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.chilik1020.hw10.databinding.ActivityMainBinding
import com.chilik1020.hw10.service.MusicPlayer
import com.chilik1020.hw10.service.MusicService
import com.chilik1020.hw10.service.NotificationUtil
import com.chilik1020.hw10.utils.SONG_NAME_KEY


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var path: String

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

}