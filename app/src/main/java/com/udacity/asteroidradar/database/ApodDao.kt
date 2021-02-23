package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.PictureOfDay

@Dao
interface ApodDao {

    @Query("select * from table_picture_of_the_day")
    fun getPictureOfTheDay(): LiveData<DatabasePictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(vararg pictureOfDay: DatabasePictureOfDay)
}