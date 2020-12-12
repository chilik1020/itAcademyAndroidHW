package com.chilik1020.weatherappmvp.data.entities

class WeatherCurrentTopObject(
    val id: Int,
    val name: String,
    val coord: Coord,
    val weatherList: List<Weather>,
    val main: Main
)

class WeatherForecastTopObject(
    val lat: String,
    val lon: String,
    val current: WeatherAtTimeStamp,
    val hourlyList: List<WeatherAtTimeStamp>
)

class WeatherAtTimeStamp(
    val dt: Long,
    val temp: String,
    val weatherList: List<Weather>
)

class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

class Main(
    val temp: String
)

class Coord(
    val lon: String,
    val lat: String
)

enum class Units(val value: String) {
    METRIC("metric"),
    IMPERIAL("imperial")
}