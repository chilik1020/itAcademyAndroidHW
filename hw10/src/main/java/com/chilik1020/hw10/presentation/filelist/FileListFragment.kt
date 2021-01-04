package com.chilik1020.hw10.presentation.filelist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilik1020.hw10.data.FileModel
import com.chilik1020.hw10.data.FileType
import com.chilik1020.hw10.databinding.FragmentFileListBinding
import com.chilik1020.hw10.presentation.OnFolderOpenActionListener
import com.chilik1020.hw10.utils.BASE_LOG
import com.chilik1020.hw10.utils.FILE_PATH_KEY
import com.chilik1020.hw10.utils.createFileModelsListFromDirPath

class FileListFragment : Fragment() {

    private var onFolderOpenListener: OnFolderOpenActionListener? = null
    private lateinit var binding: FragmentFileListBinding
    private var folderPath: String? = null

    private val listener: ((FileModel) -> Unit) = { file ->
        Log.d(BASE_LOG, "CLICK ACTIVITY")
        when (file.type) {
            FileType.FOLDER -> {
                onFolderOpenListener?.openFolder(file.path)
            }
            FileType.MUSIC_FILE -> {
                Log.d(BASE_LOG, "Music file!")
            }
        }
    }
    private val fileAdapter = FilesAdapter(listener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFolderOpenActionListener) {
            onFolderOpenListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.getString(FILE_PATH_KEY).let { folderPath = it }
        Log.d(BASE_LOG, "Path = $folderPath")
        binding = FragmentFileListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getFileModelListForAdapter()
    }

    private fun initViews() {
        binding.recyclerViewFiles.apply {
            adapter = fileAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun getFileModelListForAdapter() {
        folderPath?.let {
            val fileModelsList = createFileModelsListFromDirPath(it)
            binding.tvEmptyFolder.visibility =
                if (fileModelsList.isEmpty()) View.VISIBLE else View.GONE
            fileAdapter.setData(fileModelsList)
        }
    }
}