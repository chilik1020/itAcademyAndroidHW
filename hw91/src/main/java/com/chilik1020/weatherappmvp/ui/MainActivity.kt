package com.chilik1020.weatherappmvp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.weatherappmvp.databinding.ActivityMainBinding
import com.chilik1020.weatherappmvp.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, WeatherFragment())
            .commit()
    }
}