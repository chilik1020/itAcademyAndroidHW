package com.chilik1020.weatherappmvp.presentation.city

import com.chilik1020.weatherappmvp.domain.CityAsActiveUseCase
import com.chilik1020.weatherappmvp.domain.CityListUseCase
import com.chilik1020.weatherappmvp.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvp.presentation.models.CityUiModel
import com.chilik1020.weatherappmvp.presentation.models.CityUiToDomainMapper

class CityPresenter(
    private val cityListUseCase: CityListUseCase,
    private val cityAsActiveUseCase: CityAsActiveUseCase,
    private val cityUiToDomainMapper: CityUiToDomainMapper,
    private val cityDomainToUiMapper: CityDomainToUiMapper
) : CityContract.Presenter {

    private var view: CityContract.View? = null

    override fun loadCities() {
        val subscribe = cityListUseCase.getCities()
            .subscribe(
                {
                    val list = it.map { cityDomain -> cityDomainToUiMapper.map(cityDomain) }
                    view?.render(CityViewState.Loaded(list))
                },
                {
                    view?.render(CityViewState.Error(it.message.toString()))
                }
            )
    }

    override fun setCityAsActive(city: CityUiModel) {
        val subscribe = cityAsActiveUseCase.setCityAsActive(cityUiToDomainMapper.map(city))
            .subscribe(
                {
                    loadCities()
                },
                {
                    view?.render(CityViewState.Error(it.message.toString()))
                }
            )
    }

    override fun attachView(view: CityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}