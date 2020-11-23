package com.chilik1020.hw6.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chilik1020.hw6.R
import com.chilik1020.hw6.model.StorageType
import com.chilik1020.hw6.utils.SELECTED_STORAGE_PREF
import kotlinx.android.synthetic.main.fragment_settings.switchStorageType

class SettingsFragment : Fragment() {

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val storage = preferences.getString(SELECTED_STORAGE_PREF, null)
        storage?.let {
            switchStorageType.isChecked = StorageType.valueOf(it) == StorageType.EXTERNAL
        }

        switchStorageType.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateStorageType(StorageType.EXTERNAL)
            } else {
                updateStorageType(StorageType.INTERNAL)
            }
        }
    }

    private fun updateStorageType(type: StorageType) {
        preferences.edit()
            .putString(SELECTED_STORAGE_PREF, type.toString())
            .apply()
    }
}