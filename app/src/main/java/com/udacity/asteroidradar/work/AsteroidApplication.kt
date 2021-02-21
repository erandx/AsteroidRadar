package com.udacity.asteroidradar.work

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AsteroidApplication: Application() {

    val applicationScore = CoroutineScope(Dispatchers.Default)


    /*
    * onCreate get's called before the screen is shown to the user
     */
    override fun onCreate() {
        super.onCreate()
    }
}