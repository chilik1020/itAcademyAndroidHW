package com.chilik1020.hw6.ui.filelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chilik1020.hw6.R
import java.io.File

class FileExplorerFragment : Fragment() {

    private val filesAdapter: FilesAdapter = FilesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val file = File(context?.filesDir?.absolutePath)
        Log.d("FELIDIR", file.toString())
        file.listFiles()?.forEach {
            Log.d("FELIDIR", it.absolutePath)
        }
    }
}