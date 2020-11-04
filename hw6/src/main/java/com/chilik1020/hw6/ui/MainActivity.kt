package com.chilik1020.hw6.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.chilik1020.hw6.R
import com.chilik1020.hw6.ui.filelist.FileExplorerFragment
import kotlinx.android.synthetic.main.activity_main.fragment_container

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment_container?.let {
            if (savedInstanceState != null) {
                return
            }

            supportFragmentManager.commit {
                add<FileExplorerFragment>(R.id.fragment_container, null, intent.extras)
            }
        }
    }
}