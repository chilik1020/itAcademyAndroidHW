package com.chilik1020.weatherappmvp.model.entities

data class WeatherFore(
    val id: Int,
    val name: String,
    val coord: Coord,
    val weatherList: List<Weather>,
    val main: Main
)

data class WeatherForecastTopObject(
    val lat: String,
    val lon: String,
    val current: WeatherAtTimeStamp,
    val hourlyList: List<WeatherAtTimeStamp>
)

data class WeatherAtTimeStamp(
    val dt: Long,
    val temp: String,
    val weatherList: List<Weather>
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

data class Coord(
    val lon: String,
    val lat: String
)