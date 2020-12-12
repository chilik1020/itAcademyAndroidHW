package com.chilik1020.weatherappmvp.presentation.addcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.chilik1020.weatherappmvp.databinding.FragmentDialogAddCityBinding
import org.koin.android.ext.android.inject

class AddCityDialogFragment : DialogFragment(), AddCityContract.View {

    private lateinit var binding: FragmentDialogAddCityBinding
    private val presenter: AddCityContract.Presenter by inject()

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
        presenter.attachView(this)
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun render(state: AddCityViewState) {
        when (state) {
            AddCityViewState.Loading -> {

            }
            AddCityViewState.Loaded -> {

            }
            is AddCityViewState.Error -> {

            }
        }
    }


    private fun initViews() {
        binding.btnDone.setOnClickListener {
            if (binding.etCityName.text.isNotEmpty()) {
                presenter.fetchWeatherForCityName(binding.etCityName.text.toString())
            }
        }

        binding.btnCancel.setOnClickListener { dismiss() }
    }
}