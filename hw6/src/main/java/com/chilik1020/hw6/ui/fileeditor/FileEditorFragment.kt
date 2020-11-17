package com.chilik1020.hw6.ui.fileeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chilik1020.hw6.R
import com.chilik1020.hw6.utils.FILE_PATH_KEY
import com.chilik1020.hw6.utils.readTextFromFile
import com.chilik1020.hw6.utils.writeTexInFile
import kotlinx.android.synthetic.main.fragment_file_editor.*


class FileEditorFragment : Fragment() {

    private var filePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.getString(FILE_PATH_KEY)?.let { filePath = it }
        return inflater.inflate(R.layout.fragment_file_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabSaveFileChanges.setOnClickListener {
            filePath?.let { file ->
                writeTexInFile(file, etTextFileContent.text.toString())
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        filePath?.let { etTextFileContent.setText(readTextFromFile(it)) }
    }
}