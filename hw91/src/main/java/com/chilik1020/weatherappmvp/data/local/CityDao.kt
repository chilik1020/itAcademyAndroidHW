package com.chilik1020.weatherappmvp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chilik1020.weatherappmvp.data.entities.City
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    fun getCities(): Flowable<List<City>>

    @Insert
    fun addCity(city: City): Maybe<Long>
}