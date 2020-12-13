package com.chilik1020.weatherappmvvm.presentation.city

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chilik1020.weatherappmvvm.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityListUseCase
import com.chilik1020.weatherappmvvm.domain.Result
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel
import com.chilik1020.weatherappmvvm.presentation.models.CityUiToDomainMapper
import com.chilik1020.weatherappmvvm.utils.LOG_TAG

class CityViewModel(
    private val cityListUseCase: CityListUseCase,
    private val cityAsActiveUseCase: CityAsActiveUseCase,
    private val cityUiToDomainMapper: CityUiToDomainMapper,
    private val cityDomainToUiMapper: CityDomainToUiMapper
) : ViewModel() {

    private val mutableViewState: MutableLiveData<CityViewState> =
        MutableLiveData(CityViewState.Loading)
    val viewState: LiveData<CityViewState>
        get() = mutableViewState

    private val loadCitiesListener = CityListUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                val list =
                    result.data.map { cityDomain -> cityDomainToUiMapper.map(cityDomain) }.toList()
                mutableViewState.value = CityViewState.Loaded(list)
            }
            is Result.Failure -> {
                mutableViewState.value = CityViewState.Error(result.error.toString())
            }
        }
    }

    private val setCityAsActiveListener = CityAsActiveUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                Log.d(LOG_TAG, "setCityAsActiveListener onSuccess")
                loadCities()
            }
            is Result.Failure -> {
                mutableViewState.value = CityViewState.Error("Error while updating city")
                Log.d(LOG_TAG, result.error.toString())
            }
        }
    }

    fun loadCities() {
        mutableViewState.value = CityViewState.Loading
        cityListUseCase.getCities(loadCitiesListener)
    }

    fun setCityAsActive(city: CityUiModel) {
        cityAsActiveUseCase.setCityAsActive(cityUiToDomainMapper.map(city), setCityAsActiveListener)
    }
}