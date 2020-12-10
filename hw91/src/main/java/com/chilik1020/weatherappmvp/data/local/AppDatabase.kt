package com.chilik1020.weatherappmvp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chilik1020.weatherappmvp.data.entities.City

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val cityDao: CityDao
}