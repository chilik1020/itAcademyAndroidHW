package com.chilik1020.hw6.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.chilik1020.hw6.R
import com.chilik1020.hw6.model.StorageType
import com.chilik1020.hw6.ui.filelist.FileExplorerFragment
import com.chilik1020.hw6.utils.FILE_PATH_KEY
import com.chilik1020.hw6.utils.SELECTED_STORAGE_PREF
import com.chilik1020.hw6.utils.SETTINGS_FRAGMENT_TAG
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrentStorageFromPref()
        updateRootFragment(savedInstanceState)
    }

    private fun getCurrentStorageFromPref() {
        path = filesDir.absolutePath
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val storage = preferences.getString(SELECTED_STORAGE_PREF, null)

        storage?.let {
            if (StorageType.valueOf(it) == StorageType.EXTERNAL) {
                getExternalFilesDir(null)?.let { externalStorage ->
                    path = externalStorage.absolutePath
                }
            }
        }
    }

    private fun updateRootFragment(state: Bundle?) {
        fragment_container?.let {
            if (state != null) {
                return
            }

            supportFragmentManager.commit {
                val args = Bundle()
                args.putString(FILE_PATH_KEY, path)
                add<FileExplorerFragment>(R.id.fragment_container, path, args)
                addToBackStack(path)
            }
        }
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        when {
            fragments.size == 1 -> {
                finish()
            }
            fragments.last().tag == SETTINGS_FRAGMENT_TAG -> {
                getCurrentStorageFromPref()
                clearBackStackFragments()
                supportFragmentManager.commit {
                    val args = Bundle()
                    args.putString(FILE_PATH_KEY, path)
                    replace(R.id.fragment_container, FileExplorerFragment::class.java, args)
                }
            }
        }
        super.onBackPressed()
    }

    private fun clearBackStackFragments() {
        val count: Int = supportFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            supportFragmentManager.popBackStack()
        }
    }
}