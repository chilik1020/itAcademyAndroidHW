package com.chilik1020.hw10.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.chilik1020.hw10.R
import com.chilik1020.hw10.databinding.ActivityMainBinding
import com.chilik1020.hw10.utils.FILE_PATH_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            return
        }
        path = filesDir.absolutePath

        supportFragmentManager.commit {
            val args = Bundle()
            args.putString(FILE_PATH_KEY, path)
            add<FileListFragment>(R.id.fragmentContainer, path, args)
            addToBackStack(path)
        }
    }
}