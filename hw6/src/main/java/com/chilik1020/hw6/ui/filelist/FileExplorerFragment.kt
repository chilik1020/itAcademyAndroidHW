package com.chilik1020.hw6.ui.filelist

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
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
import com.chilik1020.hw6.model.StorageType
import com.chilik1020.hw6.ui.fileeditor.FileEditorFragment
import com.chilik1020.hw6.ui.settings.SettingsFragment
import com.chilik1020.hw6.utils.FILE_PATH_KEY
import com.chilik1020.hw6.utils.SELECTED_STORAGE_PREF
import com.chilik1020.hw6.utils.SETTINGS_FRAGMENT_TAG
import com.chilik1020.hw6.utils.createFileModelsListFromDirPath
import com.chilik1020.hw6.utils.createNewFile
import com.chilik1020.hw6.utils.createNewFolder
import com.chilik1020.hw6.utils.deleteFile
import com.chilik1020.hw6.utils.showAlertDialog
import kotlinx.android.synthetic.main.fragment_file_explorer.fabDirectoryCreate
import kotlinx.android.synthetic.main.fragment_file_explorer.fabFileCreate
import kotlinx.android.synthetic.main.fragment_file_explorer.llDirectoryCreate
import kotlinx.android.synthetic.main.fragment_file_explorer.llFileCreate
import kotlinx.android.synthetic.main.fragment_file_explorer.mainFab
import kotlinx.android.synthetic.main.fragment_file_explorer.recyclerViewFiles
import kotlinx.android.synthetic.main.fragment_file_explorer.toolbar
import kotlinx.android.synthetic.main.fragment_file_explorer.tvCurrentFolder
import kotlinx.android.synthetic.main.fragment_file_explorer.tvEmptyFolder

class FileExplorerFragment : Fragment() {

    private var folderPath: String? = null
    private lateinit var storageType: StorageType

    private var fileClicked: FileModel? = null
    private lateinit var dialogInputText: AppCompatEditText

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
                    add<FileExplorerFragment>(R.id.fragment_container, path, args)
                    addToBackStack(path)
                }
            } else {
                requireActivity().supportFragmentManager.commit {
                    val path = file.path
                    val args = Bundle()
                    args.putString(FILE_PATH_KEY, path)
                    add<FileEditorFragment>(R.id.fragment_container, path, args)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        initViews()
        getTypeOfStorageFromPref()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItemSettings) {
            requireActivity().supportFragmentManager.commit {
                add<SettingsFragment>(R.id.fragment_container, SETTINGS_FRAGMENT_TAG, null)
                addToBackStack(SETTINGS_FRAGMENT_TAG)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTypeOfStorageFromPref() {
        storageType = StorageType.INTERNAL
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        pref.getString(SELECTED_STORAGE_PREF, null)?.let {
            if (StorageType.valueOf(it) == StorageType.EXTERNAL) {
                storageType = StorageType.EXTERNAL
            }
        }
        tvCurrentFolder.text = if (storageType == StorageType.INTERNAL) "Internal" else "External"
    }

    private fun getFileModelListForAdapter() {
        folderPath?.let {
            val fileModelsList = createFileModelsListFromDirPath(it)
            tvEmptyFolder.visibility = if (fileModelsList.isEmpty()) View.VISIBLE else View.GONE
            filesAdapter.setData(fileModelsList)
        }
    }

    private fun initViews() {
        animationFabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open_animation)
        animationFabClose =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close_animation)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }

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
}