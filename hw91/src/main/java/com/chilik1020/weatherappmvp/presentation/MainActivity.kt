package com.chilik1020.weatherappmvp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.weatherappmvp.databinding.ActivityMainBinding
import com.chilik1020.weatherappmvp.presentation.weather.WeatherFragment

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