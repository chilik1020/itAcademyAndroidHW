package com.chilik1020.hw6.ui.filelist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilik1020.hw6.R
import com.chilik1020.hw6.model.FileModel
import com.chilik1020.hw6.model.FileType
import com.chilik1020.hw6.utils.*
import kotlinx.android.synthetic.main.fragment_file_explorer.*
import java.io.File
import kotlin.random.Random


class FileExplorerFragment : Fragment() {

    private val onFileClickListener: OnFileItemClickListener = object : OnFileItemClickListener {
        override fun onClick(file: FileModel) {
            if (file.type == FileType.FOLDER) {
                requireActivity().supportFragmentManager.commit {
                    val path = file.path
                    val args: Bundle = Bundle()
                    args.putString(FILE_PATH_KEY, path)
                    add<FileExplorerFragment>(R.id.fragment_container, null, args)
                    addToBackStack(path)
                }
            }
        }

        override fun onLongClick(file: FileModel): Boolean {
            val message =
                if (file.type == FileType.FILE) R.string.message_delete_file else R.string.message_delete_directory
            val alertDialog = AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton(R.string.button_yes) { dialog: DialogInterface, _: Int ->
                    deleteFile(file.path)
                    getFileModelListForAdapter()
                    dialog.cancel()
                }.setNegativeButton(R.string.button_cancel) { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }.create()
            alertDialog.show()
            return true
        }
    }

    private val filesAdapter: FilesAdapter = FilesAdapter(onFileClickListener)
    private var folderPath: String? = null

    private var isMainFabExpanded = false
    private lateinit var animationFabOpen: Animation
    private lateinit var animationFabClose: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val path = arguments?.getString(FILE_PATH_KEY)
        path?.let { folderPath = it }
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
        animationFabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open_animation)
        animationFabClose =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close_animation)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        getFileModelListForAdapter()
    }

    private fun getFileModelListForAdapter() {
        folderPath?.let {
            val file = File(it)
            tvCurrentFolder.text = file.path
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

        mainFab.setOnClickListener {
            if (isMainFabExpanded) {
                collapseFabMenu()
            } else {
                expandFabMenu()
            }

        }
        fabDirectoryCreate.setOnClickListener {
            folderPath?.let { path -> createNewFolder(path, "innerDir${Random.nextInt()}") }
            getFileModelListForAdapter()
        }
        fabFileCreate.setOnClickListener {
            folderPath?.let { path -> createNewFile(path, "innerDir${Random.nextInt()}") }
            getFileModelListForAdapter()
        }
    }

    private fun expandFabMenu() {
        ViewCompat.animate(mainFab)
            .rotation(45.0F)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(10.0F))
            .start()

        llFileCreate.startAnimation(animationFabOpen)
        llDirectoryCreate.startAnimation(animationFabOpen)
        llFileCreate.isClickable = true
        llDirectoryCreate.isClickable = true
        isMainFabExpanded = true
    }

    private fun collapseFabMenu() {
        ViewCompat.animate(mainFab)
            .rotation(0.0F)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(10.0F))
            .start()

        llFileCreate.startAnimation(animationFabClose)
        llDirectoryCreate.startAnimation(animationFabClose)
        llFileCreate.isClickable = false
        llDirectoryCreate.isClickable = false
        isMainFabExpanded = false
    }

    companion object {
        const val LOG = "$BASE_LOG:ExplFrag"
    }
}