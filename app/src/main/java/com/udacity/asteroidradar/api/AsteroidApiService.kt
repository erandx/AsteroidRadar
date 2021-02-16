package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Use of Moshi Builder, creating a Moshi Object
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//Using Retrofit.Builder to Create Retrofit Object wit BASE_URL
private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()

interface AsteroidApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date") startDate: String,
                     @Query("end_date") endDate: String,
                    @Query("api_key") apiKey: String
    ): String

    @GET("planetary/apod?api_key=${Constants.API_KEY}")
    suspend fun getPictureOfTheDay(): PictureOfDay
}

object AsteroidApi{
    val retrofitService: AsteroidApiService by lazy{
        retrofit.create(AsteroidApiService::class.java)
    }
}