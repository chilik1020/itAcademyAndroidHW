package com.chilik1020.hw10.presentation

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.chilik1020.hw10.R
import com.chilik1020.hw10.databinding.ActivityMainBinding
import com.chilik1020.hw10.presentation.filelist.FileListFragment
import com.chilik1020.hw10.utils.BASE_LOG
import com.chilik1020.hw10.utils.FILE_PATH_KEY


class MainActivity : AppCompatActivity(), OnFolderOpenActionListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            return
        }

        val files = assets.list("")?.filter { it.contains(".mp3") }
        Log.d(BASE_LOG, files!!.first())
        playAssetSound(applicationContext, "Rammstein - Mein Herz Brennt.mp3")
        path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath!!
    }


    override fun openFolder(path: String) {
        Log.d(BASE_LOG, "OPEN FOLDER $path")
        addFileListFragmentToBackStack(path)
    }

    private fun addFileListFragmentToBackStack(path: String) {
        supportFragmentManager.commit {
            val args = Bundle()
            args.putString(FILE_PATH_KEY, path)
            add<FileListFragment>(R.id.fragmentContainer, path, args)
            addToBackStack(path)
        }
    }

    fun playAssetSound(context: Context, soundFileName: String) {
        try {
            val mediaPlayer = MediaPlayer()
            val descriptor: AssetFileDescriptor = context.getAssets().openFd(soundFileName)
            mediaPlayer.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            descriptor.close()
            mediaPlayer.prepare()
            mediaPlayer.setVolume(1f, 1f)
            mediaPlayer.setLooping(false)
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}