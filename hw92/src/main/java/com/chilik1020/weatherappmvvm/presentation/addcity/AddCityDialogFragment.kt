package com.chilik1020.weatherappmvvm.presentation.addcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.chilik1020.weatherappmvvm.databinding.FragmentDialogAddCityBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class AddCityDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogAddCityBinding
    private val viewModel: AddCityViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun render(state: AddCityViewState) {
        when (state) {
            AddCityViewState.Loading -> {
                binding.pbCityLoading.visibility = View.VISIBLE
            }
            AddCityViewState.Loaded -> {
                binding.pbCityLoading.visibility = View.GONE
                dismiss()
            }
            is AddCityViewState.Error -> {
                binding.pbCityLoading.visibility = View.GONE
                Snackbar.make(binding.root, state.error, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        binding.btnDone.setOnClickListener {
            if (binding.etCityName.text.isNotEmpty()) {
                viewModel.fetchWeatherForCityName(binding.etCityName.text.toString())
            }
        }

        binding.btnCancel.setOnClickListener { dismiss() }

        viewModel.viewState.observe(viewLifecycleOwner) { render(it) }
    }
}