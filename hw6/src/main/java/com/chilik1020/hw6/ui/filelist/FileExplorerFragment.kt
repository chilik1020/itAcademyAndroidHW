package com.chilik1020.hw6.ui.filelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilik1020.hw6.R
import com.chilik1020.hw6.model.FileModel
import com.chilik1020.hw6.model.FileType
import com.chilik1020.hw6.utils.BASE_LOG
import com.chilik1020.hw6.utils.FILE_PATH_KEY
import com.chilik1020.hw6.utils.createNewFolder
import kotlinx.android.synthetic.main.fragment_file_explorer.*
import java.io.File

class FileExplorerFragment : Fragment() {

    private val onFileClickListener: OnFileItemClickListener = OnFileItemClickListener {
        if (it.type == FileType.FOLDER) {
            requireActivity().supportFragmentManager.commit {
                val path = "${it.path}\\${it.name}"
                val args: Bundle = Bundle()
                args.putString(FILE_PATH_KEY, path)
                add<FileExplorerFragment>(R.id.fragment_container, null, args)
                addToBackStack(path)
            }
        }
    }

    private val filesAdapter: FilesAdapter = FilesAdapter(onFileClickListener)
    private var folderPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val path = arguments?.getString(FILE_PATH_KEY)
        path?.let { folderPath = it }
        Log.d(LOG, "path = $folderPath")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        getFileModelListForAdapter()
    }

    private fun getFileModelListForAdapter() {
        folderPath?.let {
            val file = File(it)

            val listFiles = file.listFiles()
            if (listFiles.isNullOrEmpty()) {
                tvEmptyFolder.visibility = View.VISIBLE
                filesAdapter.setData(emptyList())
            } else {
                tvEmptyFolder.visibility = View.GONE
                filesAdapter.setData(listFiles
                    .map { fileInMap ->
                        FileModel(
                            fileInMap.name,
                            fileInMap.absolutePath,
                            type = if (fileInMap.isDirectory) FileType.FOLDER else FileType.FILE
                        )
                    }
                    .sortedBy { fileInSort ->
                        fileInSort.type
                    })
            }
        }
    }

    private fun initViews() {
        recyclerViewFiles.apply {
            adapter = filesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        fab.setOnClickListener {
            folderPath?.let{path -> createNewFolder(path, "innerFolder0")}
        }
    }

    companion object {
        const val LOG = "$BASE_LOG:ExplFrag"
    }
}