package com.chilik1020.weatherappmvp.model.entities

data class WeatherTopObject(
    val id: Int,
    val name: String,
    val weather: Weather,
    val main: Main
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: String
)