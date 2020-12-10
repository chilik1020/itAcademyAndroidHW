package com.chilik1020.weatherappmvp.utils

const val REQUEST_CURRENT_BASE_URL =
    "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s"
const val REQUEST_FORECAST_BASE_URL =
    "http://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&appid=%s&units=%s&exclude=minutely&exclude=alerts&exclude=daily"
const val API_KEY = ""
const val ICON_BASE_URL = "http://openweathermap.org/img/wn/%s@2x.png"

const val LOG_TAG = "WEATHERTAG"

const val SHARED_PREF_NAME = "sharedPref"