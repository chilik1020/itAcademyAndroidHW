package com.chilik1020.weatherappmvp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chilik1020.weatherappmvp.data.entities.City
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    fun getCities(): Flowable<List<City>>

    @Query("SELECT * FROM cities WHERE isCurrentCity = 1")
    fun getActiveCity(): Maybe<City>

    @Insert
    fun addCity(city: City): Maybe<Long>

    @Query("UPDATE cities SET isCurrentCity = 0 WHERE isCurrentCity = 1")
    fun clearIsActiveCityField(): Single<Int>

    @Query("UPDATE cities SET isCurrentCity = 1 WHERE name = :name")
    fun updateCityStatusByName(name: String): Single<Int>
}