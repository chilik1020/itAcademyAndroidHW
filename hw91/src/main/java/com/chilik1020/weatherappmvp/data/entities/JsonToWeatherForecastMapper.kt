package com.chilik1020.weatherappmvp.data.entities

import org.json.JSONObject

class JsonToWeatherForecastMapper : (String) -> WeatherForecastTopObject {
    override fun invoke(json: String): WeatherForecastTopObject {
        val jsonMainObject = JSONObject(json)

        val lat = jsonMainObject.getString("lat")
        val lon = jsonMainObject.getString("lon")

        val jsonCurrent = jsonMainObject.getJSONObject("current")
        val current = getWeatherAtTimeStampFromJsonObject(jsonCurrent)

        val jsonHourly = jsonMainObject.getJSONArray("hourly")
        val weatherHourlyList = mutableListOf<WeatherAtTimeStamp>()
        for (i in 0 until jsonHourly.length()) {
            val weatherAtTimeStamp =
                getWeatherAtTimeStampFromJsonObject(jsonHourly.getJSONObject(i))
            weatherHourlyList.add(weatherAtTimeStamp)
        }
        return WeatherForecastTopObject(
            lat = lat,
            lon = lon,
            current = current,
            hourlyList = weatherHourlyList
        )
    }

    private fun getWeatherAtTimeStampFromJsonObject(jsonObject: JSONObject): WeatherAtTimeStamp {
        val dt = jsonObject.getLong("dt")
        val temp = jsonObject.getString("temp")
        val jsonWeatherArray = jsonObject.getJSONArray("weather")
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
        return WeatherAtTimeStamp(
            dt = dt,
            temp = temp,
            weatherList = weatherList
        )
    }
}