package com.chilik1020.weatherappmvvm.presentation.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chilik1020.weatherappmvvm.data.entities.Units
import com.chilik1020.weatherappmvvm.databinding.FragmentSettingsBinding
import com.chilik1020.weatherappmvvm.utils.UNITS_SYSTEM
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val pref: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val unitsSystem = pref.getString(UNITS_SYSTEM, Units.METRIC.value)
        unitsSystem?.let {
            if (it == Units.IMPERIAL.value) {
                binding.switchUnitsSystem.isChecked = true
            }
        }

        binding.switchUnitsSystem.setOnCheckedChangeListener { _, isChecked ->
            val value = if (isChecked) {
                Units.IMPERIAL.value
            } else {
                Units.METRIC.value
            }
            pref.edit().putString(UNITS_SYSTEM, value).apply()
        }
    }

}