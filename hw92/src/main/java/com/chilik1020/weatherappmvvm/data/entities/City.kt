package com.chilik1020.weatherappmvvm.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val lat: String,
    val lon: String,
    var isCurrentCity: Boolean
)