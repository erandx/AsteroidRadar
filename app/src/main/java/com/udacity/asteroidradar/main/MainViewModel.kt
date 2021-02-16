package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class AsteroidStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel() {

    private val _status = MutableLiveData<AsteroidStatus>()

    val status: LiveData<AsteroidStatus>
        get() = _status

    private val _asteroids = MutableLiveData<String>()
    val asteroids: LiveData<String>
        get() = _asteroids

    private val _picture = MutableLiveData<PictureOfDay>()
    val picture: LiveData<PictureOfDay>
        get() = _picture

    init {
        getAsteroids()
        getPictureOfTheDay()
    }

    private fun getPictureOfTheDay() {
        _status.value = AsteroidStatus.LOADING
        viewModelScope.launch {
            try {
                val pictureResult = AsteroidApi.retrofitService.getPictureOfTheDay()
                _status.value = AsteroidStatus.DONE
                _picture.value = pictureResult

            } catch (t: Throwable) {
                _status.value = AsteroidStatus.ERROR
            }
        }
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val listResult = AsteroidApi.retrofitService.getAsteroids("2021-01-08", "2021-01-01", Constants.API_KEY)
                _asteroids.value = "Success + ${listResult} Asteroids"
            } catch (t: Throwable) {
                _asteroids.value = "Failure" + t.message
            }
        }
    }

}