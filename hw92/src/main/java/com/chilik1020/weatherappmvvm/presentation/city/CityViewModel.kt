package com.chilik1020.weatherappmvvm.presentation.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chilik1020.weatherappmvvm.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityListUseCase
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel
import com.chilik1020.weatherappmvvm.presentation.models.CityUiToDomainMapper

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
    
    fun loadCities() {
        mutableViewState.value = CityViewState.Loading
        val subscribe = cityListUseCase.getCities()
            .subscribe(
                {
                    val list = it.map { cityDomain -> cityDomainToUiMapper.map(cityDomain) }
                    mutableViewState.value = CityViewState.Loaded(list)
                },
                {
                    mutableViewState.value = CityViewState.Error(it.message.toString())
                }
            )
    }

    fun setCityAsActive(city: CityUiModel) {
        val subscribe = cityAsActiveUseCase.setCityAsActive(cityUiToDomainMapper.map(city))
            .subscribe(
                {
                    loadCities()
                },
                {
                    mutableViewState.value = CityViewState.Error(it.message.toString())
                }
            )
    }
}