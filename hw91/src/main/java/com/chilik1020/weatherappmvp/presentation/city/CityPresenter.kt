package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.domain.CityListUseCase
import com.chilik1020.weatherappmvp.domain.Result
import com.chilik1020.weatherappmvp.presentation.models.CityDomainToUiMapper

class CityPresenter(
    private val cityListUseCase: CityListUseCase,
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

            }
        }
    }

    override fun loadCities() {
        cityListUseCase.getCities(loadCitiesListener)
    }

    override fun attachView(view: CityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}