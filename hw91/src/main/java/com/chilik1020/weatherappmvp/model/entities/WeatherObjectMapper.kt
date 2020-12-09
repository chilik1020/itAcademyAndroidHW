package com.chilik1020.weatherappmvp.model.entities

import org.json.JSONObject

class WeatherObjectMapper : (String) -> WeatherTopObject {
    override fun invoke(json: String): WeatherTopObject {
        val jsonMainObject = JSONObject(json)

        val id = jsonMainObject.getInt("id")
        val name = jsonMainObject.getString("name")

        val jsonWeatherArray = jsonMainObject.getJSONArray("weather")
        val weatherList = mutableListOf<Weather>()
        for (i  in 0 until jsonWeatherArray.length()) {
            val jsonWeather  = jsonWeatherArray.getJSONObject(i)
            val weather = Weather(
                id = jsonWeather.getInt("id"),
                main = jsonWeather.getString("main"),
                description = jsonWeather.getString("description"),
                icon = jsonWeather.getString("icon")
            )
            weatherList.add(weather)
        }


        val jsonMain = jsonMainObject.getJSONObject("main")
        val main = Main(
            temp = jsonMain.getString("temp")
        )

        return WeatherTopObject(
            id, name, weatherList, main
        )
    }
}