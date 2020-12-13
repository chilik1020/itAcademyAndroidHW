package com.chilik1020.weatherappmvvm.presentation.city

import android.util.Log
import com.chilik1020.weatherappmvvm.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvvm.domain.CityListUseCase
import com.chilik1020.weatherappmvvm.domain.Result
import com.chilik1020.weatherappmvvm.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvvm.presentation.models.CityUiModel
import com.chilik1020.weatherappmvvm.presentation.models.CityUiToDomainMapper
import com.chilik1020.weatherappmvvm.utils.LOG_TAG

class CityPresenter(
    private val cityListUseCase: CityListUseCase,
    private val cityAsActiveUseCase: CityAsActiveUseCase,
    private val cityUiToDomainMapper: CityUiToDomainMapper,
    private val cityDomainToUiMapper: CityDomainToUiMapper
) : CityContract.Presenter {

    private var view: CityContract.View? = null

    private val loadCitiesListener = CityListUseCase.OnFinished { result ->
        when (result) {
            is Result.Success -> {
                val list =
                    result.data.map { cityDomain -> cityDomainToUiMapper.map(cityDomain) }.toList()
                view?.render(CityViewState.Loaded(list))
            }
            is Result.Failure -> {
                view?.render(CityViewState.Error(result.error.toString()))
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
                view?.render(CityViewState.Error("Error while updating city"))
                Log.d(LOG_TAG, result.error.toString())
            }
        }
    }

    override fun loadCities() {
        cityListUseCase.getCities(loadCitiesListener)
    }

    override fun setCityAsActive(city: CityUiModel) {
        cityAsActiveUseCase.setCityAsActive(cityUiToDomainMapper.map(city), setCityAsActiveListener)
    }

    override fun attachView(view: CityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}