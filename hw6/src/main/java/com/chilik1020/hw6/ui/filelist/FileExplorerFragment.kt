package com.chilik1020.hw6.ui.filelist

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chilik1020.hw6.R
import com.chilik1020.hw6.model.FileModel
import com.chilik1020.hw6.model.FileType
import com.chilik1020.hw6.ui.fileeditor.FileEditorFragment
import com.chilik1020.hw6.utils.*
import kotlinx.android.synthetic.main.fragment_file_explorer.*

class FileExplorerFragment : Fragment() {

    private var fileClicked: FileModel? = null
    private lateinit var dialogInputText: AppCompatEditText
    private var folderPath: String? = null

    private var isMainFabExpanded = false
    private lateinit var animationFabOpen: Animation
    private lateinit var animationFabClose: Animation

    private val onFileClickListener: OnFileItemClickListener = object : OnFileItemClickListener {
        override fun onClick(file: FileModel) {
            fileClicked = file
            if (file.type == FileType.FOLDER) {
                requireActivity().supportFragmentManager.commit {
                    val path = file.path
                    val args = Bundle()
                    args.putString(FILE_PATH_KEY, path)
                    add<FileExplorerFragment>(R.id.fragment_container, null, args)
                    addToBackStack(path)
                }
            } else {
                requireActivity().supportFragmentManager.commit {
                    val path = file.path
                    val args = Bundle()
                    args.putString(FILE_PATH_KEY, path)
                    add<FileEditorFragment>(R.id.fragment_container, null, args)
                    addToBackStack(path)
                }
            }
        }

        override fun onLongClick(file: FileModel): Boolean {
            fileClicked = file
            val msg =
                if (file.type == FileType.FILE) R.string.message_delete_file else R.string.message_delete_directory

            showAlertDialog(
                requireContext(),
                msg,
                null,
                onDeleteFilePositiveButtonClickListener,
                onNegativeButtonClickListener
            )
            return true
        }
    }

    private val filesAdapter: FilesAdapter = FilesAdapter(onFileClickListener)

    private val onDeleteFilePositiveButtonClickListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialog, _ ->
            fileClicked?.let { deleteFile(it.path) }
            getFileModelListForAdapter()
            dialog.cancel()
        }

    private val onNegativeButtonClickListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialog, _ ->
            dialog.cancel()
        }

    private val onCreateDirectoryPositiveClickListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialog, _ ->
            folderPath?.let { path -> createNewFolder(path, dialogInputText.text.toString()) }
            getFileModelListForAdapter()
            dialogInputText.text?.clear()
            dialog.cancel()
        }

    private val onCreateFilePositiveClickListener: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialog, _ ->
            folderPath?.let { path -> createNewFile(path, dialogInputText.text.toString()) }
            getFileModelListForAdapter()
            dialogInputText.text?.clear()
            dialog.cancel()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.getString(FILE_PATH_KEY).let { folderPath = it }
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

    override fun onPause() {
        super.onPause()
        if (isMainFabExpanded) {
            collapseFabMenu()
        }
    }

    private fun getFileModelListForAdapter() {
        folderPath?.let {
            val toolbarName = "../${it.substringAfterLast("/")}"
            tvCurrentFolder.text = toolbarName

            val fileModelsList = createFileModelsListFromDirPath(it)
            tvEmptyFolder.visibility = if (fileModelsList.isEmpty()) View.VISIBLE else View.GONE
            filesAdapter.setData(fileModelsList)
        }
    }

    private fun initViews() {
        dialogInputText = AppCompatEditText(requireContext())

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
            collapseFabMenu()
            showAlertDialog(
                requireActivity(),
                R.string.message_type_directory_name,
                dialogInputText,
                onCreateDirectoryPositiveClickListener,
                onNegativeButtonClickListener
            )
        }

        fabFileCreate.setOnClickListener {
            collapseFabMenu()
            showAlertDialog(
                requireActivity(),
                R.string.message_type_file_name,
                dialogInputText,
                onCreateFilePositiveClickListener,
                onNegativeButtonClickListener
            )
        }
    }

    private fun expandFabMenu() {
        rotateMainFab(45.0f)
        llFileCreate.startAnimation(animationFabOpen)
        llDirectoryCreate.startAnimation(animationFabOpen)
        llFileCreate.isClickable = true
        llDirectoryCreate.isClickable = true
        isMainFabExpanded = true
    }

    private fun collapseFabMenu() {
        rotateMainFab(0.0f)
        llFileCreate.startAnimation(animationFabClose)
        llDirectoryCreate.startAnimation(animationFabClose)
        llFileCreate.isClickable = false
        llDirectoryCreate.isClickable = false
        isMainFabExpanded = false
    }

    private fun rotateMainFab(angle: Float) {
        ViewCompat.animate(mainFab)
            .rotation(angle)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(10.0F))
            .start()
    }

    companion object {
        const val LOG = "$BASE_LOG:ExplFrag"
    }
}