package com.chilik1020.weatherappmvp.data.entities

import org.json.JSONObject

class WeatherCurrentMapper : (String) -> WeatherCurrentTopObject {
    override fun invoke(json: String): WeatherCurrentTopObject {
        val jsonMainObject = JSONObject(json)

        val id = jsonMainObject.getInt("id")
        val name = jsonMainObject.getString("name")

        val jsonCoord = jsonMainObject.getJSONObject("coord")
        val coord = Coord(
            lat = jsonCoord.getString("lat"),
            lon = jsonCoord.getString("lon")
        )

        val jsonWeatherArray = jsonMainObject.getJSONArray("weather")
        val weatherList = mutableListOf<Weather>()
        for (i in 0 until jsonWeatherArray.length()) {
            val jsonWeather = jsonWeatherArray.getJSONObject(i)
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

        return WeatherCurrentTopObject(
            id, name, coord, weatherList, main
        )
    }
}