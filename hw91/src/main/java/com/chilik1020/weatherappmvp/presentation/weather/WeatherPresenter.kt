package com.chilik1020.weatherappmvp.presentation.weather

import com.chilik1020.weatherappmvp.domain.CityActiveUseCase
import com.chilik1020.weatherappmvp.domain.ForecastWeatherUseCase
import com.chilik1020.weatherappmvp.presentation.models.CityDomainToUiMapper
import com.chilik1020.weatherappmvp.presentation.models.CityUiModel
import com.chilik1020.weatherappmvp.presentation.models.WeatherForecastDomainToUiMapper

class WeatherPresenter(
    private val useCase: ForecastWeatherUseCase,
    private val cityActiveUseCase: CityActiveUseCase,
    private val cityDomainToUiMapper: CityDomainToUiMapper,
    private val forecastDomainToUiMapper: WeatherForecastDomainToUiMapper
) :
    WeatherContract.Presenter {

    private var view: WeatherContract.View? = null
    private lateinit var activeCity: CityUiModel

    override fun loadData() {
        view?.render(WeatherForecastViewState.Loading)
        val subscribe = cityActiveUseCase.getActiveCity()
            .subscribe(
                {
                    activeCity = cityDomainToUiMapper.map(it)
                    loadForecast(it.lat, it.lon)
                },
                {
                    view?.render(WeatherForecastViewState.Error("Choose city"))
                }
            )
    }

    private fun loadForecast(lat: String, lon: String) {
        val subscribe = useCase.getHourlyForecast(lat = lat, lon = lon)
            .subscribe(
                {
                    view?.render(
                        WeatherForecastViewState.Loaded(
                            forecastDomainToUiMapper.map(it),
                            activeCity
                        )
                    )
                },
                {
                    view?.render(WeatherForecastViewState.Error(it.message.toString()))
                }
            )
    }

    override fun attachView(view: WeatherContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}