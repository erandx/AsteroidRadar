package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/*
*Repository for fetching Asteroids data from the network and storing them on disk.
 */
class AsteroidRepository (private val database: AsteroidDatabase){

    /*
    * A list of Asteroids to be shown on the Screen.
    * We use Tranforations.map to convert from Asteroid Database to Asteroid
     */
    val asteroid: LiveData<List<Asteroid>> = Transformations.map(database.AsteroidDAO.getAsteroids()){
        it.asDomainModel()
    }

    //Method to refresh the Offline Cache
    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val asteroidsList = AsteroidApi.retrofitService.getAsteroids(getToday(), getLastSevenDays(), Constants.API_KEY)

            val parseResponse = parseAsteroidsJsonResult(JSONObject(asteroidsList))
    // * is the spread operator. Allows you to pass in an array to a function that expects varargs
            database.AsteroidDAO.insertAll(*parseResponse.asDatabaseModel())
        }
    }
}