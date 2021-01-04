package com.chilik1020.hw10.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chilik1020.hw10.databinding.FragmentFileListBinding

class FileListFragment : Fragment() {

    private lateinit var binding: FragmentFileListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileListBinding.inflate(inflater, container, false)
        return binding.root
    }
}