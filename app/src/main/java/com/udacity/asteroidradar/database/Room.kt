package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

//Stores a collection of Database Asteroid in a Room Database
@Dao
interface AsteroidDAO{
//
//    //Loading all Asteroids in our Main Fragment
//    @Query("select * from databaseAsteroid")
//    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>
//
//    //Upsert: Update and insert. Replace items in case of duplicates
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAsteroids(asteroids: ArrayList<DatabaseAsteroid>)

    //Upsert: Update and insert. Replace items in case of duplicates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroid: DatabaseAsteroid)


}

@Database(entities = [DatabaseAsteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase(){

    abstract val AsteroidDAO: AsteroidDAO
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