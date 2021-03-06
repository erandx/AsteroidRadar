package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.getToday

//Stores a collection of Database Asteroid in a Room Database
@Dao
interface AsteroidDAO{
//
    //Loading all Asteroids in our Main Fragment
    @Query("select * from asteroid_table ORDER BY close_approach_date")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("select * from asteroid_table WHERE close_approach_date = :date")
    fun getTodayAsteroids(date: String = getToday()): LiveData<List<DatabaseAsteroid>>

    ///add a delete query so that when asteroids are refreshed, old ones are delete

    //Upsert: Update and insert. Replace items in case of duplicates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroid: DatabaseAsteroid)
}

@Database(entities = [DatabaseAsteroid::class, DatabasePictureOfDay::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase(){
    //To get access to AsteroidDAO and ApodDao
    abstract val AsteroidDAO: AsteroidDAO
    abstract val ApodDao: ApodDao
}

//Our Singleton that ensures only one instance will be created
private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase{
    synchronized(AsteroidDatabase::class.java) {
//If INSTANCE is not initialize create a new INSTANCE.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AsteroidDatabase::class.java, "asteroid").build()
        }
    }
    return INSTANCE
}