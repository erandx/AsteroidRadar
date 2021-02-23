package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class AsteroidStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private val _status = MutableLiveData<AsteroidStatus>()
    val status: LiveData<AsteroidStatus>
        get() = _status

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture: LiveData<PictureOfDay>
        get() = _picture

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails: LiveData<Asteroid>
        get() = _navigateToAsteroidDetails

    init {
        viewModelScope.launch {
            asteroidRepository.getAsteroidSelection(AsteroidsApiFilter.SHOW_ALL)
            asteroidRepository.refreshAsteroids()
            asteroidRepository.refreshPictureOfTheDay()
        }
    }

    var asteroidList = asteroidRepository.asteroid
    val pictureOfTheDay = asteroidRepository.pictureOfDay


    //Initiate navigation to the Details Fragment on Item click
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    //We clear the LiveData to be triggered again when we return from Details Fragment
    fun displayAsteroidDetailsComplete() {
        _navigateToAsteroidDetails.value = null
    }

    fun updateFilter(filter: AsteroidsApiFilter) {
        //Observe the new Filtered Data
        asteroidList = asteroidRepository.getAsteroidSelection(filter)
    }

}